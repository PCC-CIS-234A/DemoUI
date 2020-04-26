package edu.pcc.marc.demoui.data;

import edu.pcc.marc.demoui.logic.Episode;
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

    private static String FIND_SHOWS_SQL = "SELECT TOP 50 tb.tconst, te.parentTconst, tb.primaryTitle,\n" +
            "           tb2.primaryTitle AS parentTitle, tb.startYear, RTRIM(tb.titleType) AS titleType, averageRating, numVotes\n" +
            "FROM title_basics AS tb\n" +
            "JOIN title_ratings AS tr ON tb.tconst = tr.tconst\n" +
            "JOIN title_genre AS tg ON tb.tconst = tg.tconst\n" +
            "LEFT JOIN title_episode AS te ON tb.tconst = te.tconst\n" +
            "LEFT JOIN title_basics AS tb2 ON te.parentTconst = tb2.tconst\n" +
            "WHERE numVotes > ?\n" +
            "AND tb.titleType = ?\n" +
            "AND\tgenre = ?\n" +
            "ORDER BY averageRating DESC;";

    private static final String FETCH_EPISODES_QUERY =
            "SELECT	title_episode.tconst, seasonNumber, episodeNumber, primaryTitle, startYear, averageRating, numVotes"
                    + " FROM		title_episode"
                    + " JOIN		title_basics ON title_episode.tconst = title_basics.tconst"
                    + " LEFT JOIN	title_ratings ON title_episode.tconst = title_ratings.tconst"
                    + " WHERE		parentTconst = ?"
                    + " ORDER BY	ISNULL(seasonNumber, 9999), episodeNumber, title_episode.tconst;";

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
                        rs.getString("parentTconst"),
                        rs.getString("primaryTitle"),
                        rs.getString("parentTitle"),
                        rs.getInt("startYear"),
                        rs.getString("titleType"),
                        rs.getFloat("averageRating"),
                        rs.getInt("numVotes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }


    /**
     * Fetch the episodes for a given series, along with their ratings.
     *
     * @param id The title id for the parent series.
     * @return The list of episodes for that series.
     */
    public static ArrayList<Episode> fetchEpisodes(String id) {
        ResultSet rs = null;
        ArrayList<Episode> episodes = new ArrayList<>();
        PreparedStatement statement;

        try {
            // Create a connection if there isn't one already
            connect();

            // Prepare a SQL statement
            statement = connection.prepareStatement(FETCH_EPISODES_QUERY);

            // This one has a single parameter for the role, so we bind the value of role to the parameter
            statement.setString(1, id);

            // Execute the query returning a result set
            rs = statement.executeQuery();

            // For each row in the result set, create a new User object with the specified values
            // and add it to the list of results.
            while (rs.next()) {
                // matches public Episode(String id, int season, int episode, String title, int year, float rating, int numVotes);
                episodes.add(new Episode(
                        rs.getString("tconst"),
                        rs.getInt("seasonNumber"),
                        rs.getInt("episodeNumber"),
                        rs.getString("primaryTitle"),
                        rs.getInt("startYear"),
                        rs.getFloat("averageRating"),
                        rs.getInt("numVotes")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error: Interrupted or couldn't connect to database.");
            statement = null;
            return null;
        }
        // Return the list of results. Will be an empty list if there was an error.
        return episodes;
    }
}
