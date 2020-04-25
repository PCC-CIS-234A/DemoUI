package edu.pcc.marc.demoui.ui.InfoGraph;

import edu.pcc.marc.demoui.logic.Episode;
import edu.pcc.marc.demoui.ui.components.GraphPanel;

import javax.swing.*;

public class InfoGraphForm {
    private JPanel rootPanel;
    private GraphPanel graphPanel;

    public InfoGraphForm(String id, String title) {
        // System.out.println("Showing graph for " + id);
        graphPanel.setEpisodes(Episode.fetchEpisodes(id), title);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
