package edu.pcc.marc.demoui.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Episode> expectedEpisodes = new ArrayList<Episode>(
                List.of(new Episode("tt0579539", 1, 1, "The Train Job", 2002, 8.5f, 3475),
                        new Episode("tt0579528", 1, 2, "Bushwhacked", 2002, 8.5f, 3253),
                        new Episode("tt0579532", 1, 3, "Our Mrs. Reynolds", 2002, 8.9f, 3651),
                        new Episode("tt0579530", 1, 4, "Jaynestown", 2002, 8.6f, 3414),
                        new Episode("tt0579533", 1, 5, "Out of Gas", 2002, 9.2f, 4226),
                        new Episode("tt0579537", 1, 6, "Shindig", 2002, 8.5f, 3081),
                        new Episode("tt0579534", 1, 7, "Safe", 2002, 8.3f, 2952),
                        new Episode("tt0579527", 1, 8, "Ariel", 2002, 8.8f, 3258),
                        new Episode("tt0579541", 1, 9, "War Stories", 2002, 8.6f, 3122),
                        new Episode("tt0579531", 1, 10, "Objects in Space", 2002, 9.0f, 3701),
                        new Episode("tt0579535", 1, 11, "Serenity", 2002, 8.7f, 3688),
                        new Episode("tt0579529", 1, 12, "Heart of Gold", 2003, 8.4f, 2891),
                        new Episode("tt0579540", 1, 13, "Trash", 2003, 8.6f, 3106),
                        new Episode("tt0579538", 1, 14, "The Message", 2003, 8.5f, 2980))
        );
        assertEquals(expectedEpisodes, episodes);
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
        assertEquals(2020, e.getYear());
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