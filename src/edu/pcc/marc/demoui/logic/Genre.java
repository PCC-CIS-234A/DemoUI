package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class Genre {
    private String name;

    public static final String ALL_GENRES = "-- All Genres --";

    public Genre(String n) {
        name = n;
    }

    public static ArrayList<Genre> getAllGenres() {
        return Database.getAllGenres();
    }

    public String getName() {
        return name;
    }
}
