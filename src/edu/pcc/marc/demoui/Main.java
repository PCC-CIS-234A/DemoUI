package edu.pcc.marc.demoui;

import edu.pcc.marc.demoui.ui.InfoGraph.InfoGraphForm;
import edu.pcc.marc.demoui.ui.MainUI.MainUIForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {
        MainUIForm ui = new MainUIForm();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showSeriesInfo(String id, String title) {
        // System.out.println("Series info for " + id);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        InfoGraphForm form = new InfoGraphForm(id, title);
        JPanel root = form.getRootPanel();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
