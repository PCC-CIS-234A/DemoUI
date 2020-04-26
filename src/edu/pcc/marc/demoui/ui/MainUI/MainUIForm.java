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
    private DefaultTableModel showTableModel;
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

    private String emptyForZero(int val) {
        return (val != 0) ? "" + val : "";
    }
    private String emptyForZero(float val) {
        return (val != 0.0) ? "" + val : "";
    }

    private void showShows() {
        Integer minShows = Integer.parseInt(minVotesField.getText());
        String titleType = (String)typeCombo.getSelectedItem();
        String genre = (String)genreCombo.getSelectedItem();
        currentShows = Show.findShows(minShows, titleType, genre);
        DefaultTableModel model = (DefaultTableModel)showTable.getModel();

        createTable();
        model.setRowCount(0);
        boolean hasParentTitle = false;
        boolean hasEpisodes = false;
        for (Show show: currentShows) {
            if (show.getParentTitle() != null)
                hasParentTitle = true;
            if (show.getNumEpisodes() > 0)
                hasEpisodes = true;
            showTableModel.addRow(new Object[]{
                    show.getTitle(),
                    show.getTitleType(),
                    show.getParentTitle(),
                    emptyForZero(show.getStartYear()),
                    emptyForZero(show.getRuntimeMinutes()),
                    emptyForZero(show.getAverageRating()),
                    emptyForZero(show.getNumVotes()),
                    show.getGenres(),
                    emptyForZero(show.getNumEpisodes())
            });
        }
        if (!hasParentTitle) {
            showTable.getColumnModel().getColumn(2).setMinWidth(0);
            showTable.getColumnModel().getColumn(2).setMaxWidth(0);
        }
        if (!hasEpisodes) {
            showTable.getColumnModel().getColumn(8).setMinWidth(0);
            showTable.getColumnModel().getColumn(8).setMaxWidth(0);
        }
    }

    private void createTable() {
        // Create a default table model with three columns named Email, Password and Role, and no table data.
        showTableModel = new DefaultTableModel(
                // Initial data (empty)
                new Object[][]{},
                // Initial columns
                new Object[] { "Show Title", "Type", "Series Title", "Start Year", "Runtime", "Rating", "Votes", "Genres", "Episodes" }
        ) {
            // Do not let the user edit values in the table.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Make the userTable use that model
        showTable.setModel(showTableModel);

        // Center values in the Start Year, End Year, and Runtime columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        showTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );
        showTable.getColumnModel().getColumn(8).setCellRenderer( centerRenderer );

        // Center column headers
        ((DefaultTableCellRenderer)showTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Adjust column widths
        showTable.getColumnModel().getColumn(0).setMinWidth(200);
        showTable.getColumnModel().getColumn(1).setMinWidth(80);
        showTable.getColumnModel().getColumn(2).setMinWidth(200);
        showTable.getColumnModel().getColumn(2).setMaxWidth(250);
        showTable.getColumnModel().getColumn(7).setMinWidth(180);
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
                    Main.showSeriesInfo(currentShows.get(row).getId(), currentShows.get(row).getTitle());
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
