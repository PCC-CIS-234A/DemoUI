package edu.pcc.marc.demoui.logic;

import edu.pcc.marc.demoui.data.Database;

import java.util.ArrayList;
import java.util.Objects;

public class Episode {
    private String m_ID;
    private int m_SeasonNumber;
    private int m_EpisodeNumber;
    private String m_Title;
    private int m_Year;
    private float m_Rating;
    private int m_NumVotes;

    public Episode(String id, int season, int episode, String title, int year, float rating, int numVotes) {
        m_ID = id;
        m_SeasonNumber = season;
        m_EpisodeNumber = episode;
        m_Title = title;
        m_Year = year;
        m_Rating = rating;
        m_NumVotes = numVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() == this.getClass()) {
            Episode e = (Episode) o;
            if (!m_ID.equals(e.getID()))
                return false;
            if (m_SeasonNumber != e.getSeasonNumber())
                return false;
            if (m_EpisodeNumber != e.getEpisodeNumber())
                return false;
            if (!m_Title.equals(e.getTitle()))
                return false;
            if (m_Year != e.getYear())
                return false;
            if (m_Rating != e.getRating())
                return false;
            if (m_NumVotes != e.getNumVotes())
                return false;
            return true;
        }
        return false;
    }

    public static ArrayList<Episode> fetchEpisodes(String id) {
        // return Database.fetchEpisodes(id);
        return Database.fetchEpisodes(id);
    }

    public String getID() {
        return m_ID;
    }

    public int getSeasonNumber() {
        return m_SeasonNumber;
    }

    public int getEpisodeNumber() {
        return m_EpisodeNumber;
    }

    public String getTitle() {
        return m_Title;
    }

    public int getYear() {
        return m_Year;
    }

    public float getRating() {
        return m_Rating;
    }

    public int getNumVotes() {
        return m_NumVotes;
    }
}
