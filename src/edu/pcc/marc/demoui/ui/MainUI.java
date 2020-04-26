package edu.pcc.marc.demoui.ui;

import edu.pcc.marc.demoui.logic.Genre;
import edu.pcc.marc.demoui.logic.Show;
import edu.pcc.marc.demoui.logic.ShowType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainUI {
    private JPanel rootPanel;
    private JTable showTable;
    private JComboBox genreCombo;
    private JComboBox typeCombo;
    private JTextField minVotesField;
    private JButton episodeButton;
    private JButton imdbButton;

    public MainUI() {
        createGenreCombo();
        createTypeCombo();
        createMinVotesField();
        createTable();
        showShows();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void showShows() {
        Integer minShows = Integer.parseInt(minVotesField.getText());
        String titleType = (String)typeCombo.getSelectedItem();
        String genre = (String)genreCombo.getSelectedItem();
        ArrayList<Show> shows = Show.findShows(minShows, titleType, genre);
        DefaultTableModel model = (DefaultTableModel)showTable.getModel();
        System.out.println(genre + " " + titleType + " " + minShows);

        model.setRowCount(0);
        for (Show show : shows) {
            model.addRow(new Object[]{
                    show.getPrimaryTitle(),
                    show.getStartYear(),
                    show.getAverageRating(),
                    show.getNumVotes()
            });
        }
    }

    private void createTable() {
        showTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Title", "Year", "Rating", "Num Votes"}
        ));
        TableColumnModel columns = showTable.getColumnModel();
        columns.getColumn(0).setMinWidth(250);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
    }

    private void createGenreCombo() {
        ArrayList<Genre> genres = Genre.getAllGenres();
        genreCombo.addItem(Genre.ALL_GENRES);
        for (Genre genre : genres) {
            genreCombo.addItem(genre.getName());
        }
        genreCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    showShows();
                }
            }
        });
    }

    private void createTypeCombo() {
        ArrayList<ShowType> types = ShowType.getAllShowTypes();
        typeCombo.addItem(ShowType.ALL_TYPES);
        for (ShowType type : types) {
            typeCombo.addItem(type.getName());
        }
        typeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    showShows();
                }
            }
        });
    }

    private void createMinVotesField() {
        minVotesField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                showShows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                showShows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                showShows();
            }
        });
    }
}
