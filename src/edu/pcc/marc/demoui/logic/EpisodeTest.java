package edu.pcc.marc.demoui.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeTest {

    @Test
    void fetchEpisodesInvalidID() {
        ArrayList<Episode> episodes = Episode.fetchEpisodes("");

        assertEquals(0, episodes.size());
    }

    @Test
    void fetchEpisodesValidID() {
        ArrayList<Episode> episodes = Episode.fetchEpisodes("tt0303461");
        Episode first = new Episode("tt0579539", 1, 1, "The Train Job",
                2002, 8.5f, 3475);
        Episode last = new Episode("tt0579538", 1, 14, "The Message",
                2003, 8.5f, 2980);

        assertEquals(14, episodes.size());
        assertEquals(first, episodes.get(0));
        assertEquals(last, episodes.get(13));
    }

    @Test
    void getID() {
        Episode e = new Episode("my id", 0, 0, null, 0, 0.0f, 0);
        assertEquals("my id", e.getID());
    }

    @Test
    void getSeasonNumber() {
        Episode e = new Episode(null, 1, 0, null, 0, 0.0f, 0);
        assertEquals(1, e.getSeasonNumber());
    }

    @Test
    void getEpisodeNumber() {
        Episode e = new Episode(null, 0, 2, null, 0, 0.0f, 0);
        assertEquals(2, e.getEpisodeNumber());
    }

    @Test
    void getTitle() {
        Episode e = new Episode(null, 0, 0, "Title", 0, 0.0f, 0);
        assertEquals("Title", e.getTitle());
    }

    @Test
    void getYear() {
        Episode e = new Episode(null, 0, 0, null, 2020, 0.0f, 0);
        assertEquals(2021, e.getYear());
    }

    @Test
    void getRating() {
        Episode e = new Episode(null, 0, 0, null, 0, 5.5f, 0);
        assertEquals(5.5, e.getRating());
    }

    @Test
    void getNumVotes() {
        Episode e = new Episode(null, 0, 0, null, 0, 0.0f, 12345);
        assertEquals(12345, e.getNumVotes());
    }
}