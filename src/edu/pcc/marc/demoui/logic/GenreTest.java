package edu.pcc.marc.demoui.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {
    @Test
    void test_constructor() {
        Genre g = new Genre("test");
        assertEquals("test", g.getName());
    }

    @Test
    void test_getAllGenres() {
        ArrayList<Genre> genres = Genre.getAllGenres();
        ArrayList<Genre> expected = new ArrayList<Genre>(
            List.of(new Genre("Action"),
                    new Genre("Adult"),
                    new Genre("Adventure"),
                    new Genre("Animation"),
                    new Genre("Biography"),
                    new Genre("Comedy"),
                    new Genre("Crime"),
                    new Genre("Documentary"),
                    new Genre("Drama"),
                    new Genre("Family"),
                    new Genre("Fantasy"),
                    new Genre("Film-Noir"),
                    new Genre("Game-Show"),
                    new Genre("History"),
                    new Genre("Horror"),
                    new Genre("Music"),
                    new Genre("Musical"),
                    new Genre("Mystery"),
                    new Genre("News"),
                    new Genre("Reality-TV"),
                    new Genre("Romance"),
                    new Genre("Sci-Fi"),
                    new Genre("Short"),
                    new Genre("Sport"),
                    new Genre("Talk-Show"),
                    new Genre("Thriller"),
                    new Genre("War"),
                    new Genre("Western"))
        );
        assertEquals(expected, genres);
    }
}
