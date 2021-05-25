package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;

public class Genre {
    private String name;

    public Genre(String n) {
        name = n;
    }

    public static ArrayList<Genre> getAllGenres() {
        return Database.getAllGenres();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() == this.getClass()) {
            Genre g = (Genre) o;
            return name.equals(g.getName());
        }
        return false;
    }
}
