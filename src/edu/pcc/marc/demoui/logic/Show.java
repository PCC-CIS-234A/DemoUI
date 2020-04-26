package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class Show {
    private String id;
    private String title;
    private String type;
    private String parentTitle;
    private int start;
    private int minutes;
    private float rating;
    private int numVotes;
    private String genres;
    private int numEpisodes;
    private String parentId;

    public Show(String id, String title, String type, String parentTitle, int start, int minutes, float rating, int numVotes, String genres, int numEpisodes, String parentId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.parentTitle = parentTitle;
        this.start = start;
        this.minutes = minutes;
        this.rating = rating;
        this.numVotes = numVotes;
        this.genres = genres;
        this.numEpisodes = numEpisodes;
        this.parentId = parentId;
    }

    public static ArrayList<Show> findShows(Integer minShows, String titleType, String genre) {
        return Database.findShows(minShows, titleType, genre);
    }

    public String getTitle() {
        return title;
    }

    public Integer getStartYear() {
        return start;
    }

    public Float getAverageRating() {
        return rating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public String getTitleType() {
        return type;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public Integer getNumEpisodes() {
        return numEpisodes;
    }

    public Integer getRuntimeMinutes() {
        return minutes;
    }

    public String getGenres() {
        return genres;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }
}
