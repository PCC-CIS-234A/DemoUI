package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class Show {
    private String id;
    private String parentId;
    private String primaryTitle;
    private String parentTitle;
    private Integer startYear;
    private String titleType;
    private Float averageRating;
    private Integer numVotes;

    public Show(String i, String pid, String pt, String parti, Integer sy, String tt, Float ar, Integer nv) {
        id = i;
        parentId = pid;
        primaryTitle = pt;
        parentTitle = parti;
        startYear = sy;
        titleType = tt;
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

    public String getTitleType() {
        return titleType;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getParentTitle() {
        return parentTitle;
    }
}
