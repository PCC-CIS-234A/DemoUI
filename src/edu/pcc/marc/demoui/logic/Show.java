package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class Show {
    private String primaryTitle;
    private Integer startYear;
    private Float averageRating;
    private Integer numVotes;

    public Show(String pt, Integer sy, Float ar, Integer nv) {
        primaryTitle = pt;
        startYear = sy;
        averageRating = ar;
        numVotes = nv;
    }

    public static ArrayList<Show> findShows(Integer minShows, String titleType, String genre) {
        return Database.findShows(minShows, titleType, genre);
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }
}
