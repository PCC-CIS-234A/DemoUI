package edu.pcc.marc.demoui.ui.MainUI;

import edu.pcc.marc.demoui.Main;
import edu.pcc.marc.demoui.logic.Genre;
import edu.pcc.marc.demoui.logic.Show;
import edu.pcc.marc.demoui.logic.ShowType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.ArrayList;

public class MainUIForm {
    private JPanel rootPanel;
    private JTable showTable;
    private JComboBox genreCombo;
    private JComboBox typeCombo;
    private JTextField minVotesField;
    private JButton episodeButton;
    private JButton imdbButton;
    private ArrayList<Show> currentShows = null;
    private Show selectedShow;

    public MainUIForm() {
        createGenreCombo();
        createTypeCombo();
        createMinVotesField();
        createTable();
        createEpisodesButton();
        createIMDBButton();
        showShows();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void showShows() {
        Integer minShows = Integer.parseInt(minVotesField.getText());
        String titleType = (String)typeCombo.getSelectedItem();
        String genre = (String)genreCombo.getSelectedItem();
        currentShows = Show.findShows(minShows, titleType, genre);
        DefaultTableModel model = (DefaultTableModel)showTable.getModel();

        model.setRowCount(0);
        for (Show show : currentShows) {
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
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        TableColumnModel columns = showTable.getColumnModel();
        columns.getColumn(0).setMinWidth(250);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        showTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = showTable.getSelectedRow();
                if (row > -1) {
                    selectedShow = currentShows.get(row);
                    String type = selectedShow.getTitleType();
                    if (type.equals("tvSeries") || type.equals("tvEpisode"))
                        episodeButton.setEnabled(true);
                    else
                        episodeButton.setEnabled(false);
                    imdbButton.setEnabled(true);
                } else {
                    selectedShow = null;
                    episodeButton.setEnabled(false);
                    imdbButton.setEnabled(false);
                }
            }
        });
    }

    private void createGenreCombo() {
        ArrayList<Genre> genres = Genre.getAllGenres();
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

    private void createEpisodesButton() {
        episodeButton.setEnabled(false);
        episodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = showTable.getSelectedRow();
                if (currentShows.get(row).getParentId() == null)
                    Main.showSeriesInfo(currentShows.get(row).getId(), currentShows.get(row).getPrimaryTitle());
                else
                    Main.showSeriesInfo(currentShows.get(row).getParentId(), currentShows.get(row).getParentTitle());
            }
        });
    }

    private void createIMDBButton() {
        imdbButton.setEnabled(false);
        imdbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(
                            new URL("http://www.imdb.com/title/" + selectedShow.getId()).toURI()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
