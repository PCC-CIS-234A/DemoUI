package edu.pcc.marc.demoui.data;

import edu.pcc.marc.demoui.logic.Genre;
import edu.pcc.marc.demoui.logic.Show;
import edu.pcc.marc.demoui.logic.ShowType;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Database {
    public static Connection connection = null;
    private static String CONN_STRING = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/IMDB";
    private static String USERNAME = "275student";
    private static String PASSWORD = "275student";
    private static String GET_ALL_GENRES_SQL = "SELECT DISTINCT RTRIM(genre) AS genre FROM title_genre;";
    private static String GET_ALL_TYPES_SQL = "SELECT DISTINCT RTRIM(titleType) AS titleType FROM title_basics;";

    private static String FIND_SHOWS_SQL = "SELECT        TOP 200\n" +
            "            child.tconst,\n" +
            "            RTRIM(child.titleType) AS titleType,\n" +
            "            child.primaryTitle,\n" +
            "            parent.primaryTitle AS parentTitle,\n" +
            "            child.startYear,\n" +
            "            child.runtimeMinutes,\n" +
            "            averageRating,\n" +
            "            numVotes,\n" +
            "            (\n" +
            "                SELECT        ', ' + genre\n" +
            "                FROM        title_genre\n" +
            "                WHERE        tconst = child.tconst\n" +
            "                FOR XML PATH('')\n" +
            "            ) AS genres,\n" +
            "            (\n" +
            "                SELECT MAX(numEpisodes)\n" +
            "                FROM (\n" +
            "                  SELECT    COUNT(*) AS numEpisodes\n" +
            "                  FROM      title_episode\n" +
            "                  WHERE     parentTconst = child.tconst\n" +
            "                  UNION ALL\n" +
            "                  SELECT    COUNT(*) AS numEpisodes\n" +
            "                  FROM      title_episode\n" +
            "                  WHERE     parentTconst = parent.tconst\n" +
            "                ) AS NE\n" +
            "            ) AS numEpisodes,\n" +
            "            parent.tconst AS parentTconst\n" +
            "FROM        title_basics AS child\n" +
            "JOIN        title_ratings ON child.tconst = title_ratings.tconst\n" +
            "JOIN        title_genre ON child.tconst = title_genre.tconst\n" +
            "LEFT JOIN    title_episode ON child.tconst = title_episode.tconst\n" +
            "LEFT JOIN    title_basics AS parent ON title_episode.parentTconst = parent.tconst\n" +
            "WHERE        numVotes > ?\n" +
            "AND            child.titleType = ?\n" +
            "AND            genre = ?\n" +
            "GROUP BY    child.tconst, child.titleType, child.primaryTitle, parent.primaryTitle, child.startYear, child.runtimeMinutes, averageRating, numVotes, parent.tconst\n" +
            "ORDER BY    averageRating DESC;";

    public static void connect() {
        if (connection != null) {
            return;
        } else {
            try {
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                exit(-1);
            }
        }
    }

    public static ArrayList<Genre> getAllGenres() {
        connect();
        ArrayList<Genre> genres = new ArrayList<Genre>();

        try {
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_GENRES_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                genres.add(new Genre(rs.getString("genre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }


    public static ArrayList<ShowType> getAllShowTypes() {
        connect();
        ArrayList<ShowType> types = new ArrayList<ShowType>();

        try {
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_TYPES_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                types.add(new ShowType(rs.getString("titleType")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static ArrayList<Show> findShows(Integer minShows, String titleType, String genre) {
        connect();
        ArrayList<Show> shows = new ArrayList<Show>();

        try {
            PreparedStatement stmt = connection.prepareStatement(FIND_SHOWS_SQL);
            stmt.setInt(1, minShows);
            stmt.setString(2, titleType);
            stmt.setString(3, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // primaryTitle, startYear, averageRating, numVotes
                shows.add(new Show(
                        rs.getString("tconst"),
                        rs.getString("primaryTitle"),
                        rs.getString("titleType"),
                        rs.getString("parentTitle"),
                        rs.getInt("startYear"),
                        rs.getInt("runtimeMinutes"),
                        rs.getFloat("averageRating"),
                        rs.getInt("numVotes"),
                        rs.getString("genres").substring(2),
                        rs.getInt("numEpisodes"),
                        rs.getString("parentTconst")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }
}
