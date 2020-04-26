package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class ShowType {
    private String name;

    public static final String ALL_TYPES = "-- All Types --";

    public ShowType(String n) {
        name = n;
    }

    public static ArrayList<ShowType> getAllShowTypes() {
        return Database.getAllShowTypes();
    }

    public String getName() {
        return name;
    }
}
