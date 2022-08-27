package src.gui.view;

import lombok.Data;
import src.errorHandler.Error;
import src.gui.controller.ActionManager;

import javax.swing.*;
import java.awt.*;

@Data
public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private JTable jTable;
    private JScrollPane jsp;
    private JPanel bottomStatus;
    private JTextArea textArea;
    private ActionManager actionManager;
    private String query = "var query = new Query(\"employees\")";

    private MainFrame() {
    }

    public static MainFrame getInstance() {

        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    private void initialise() {

        actionManager = new ActionManager();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(600, 400));
        jTable.setFillsViewportHeight(true);
        jTable.getTableHeader().setBackground(Color.GRAY);
        jTable.setGridColor(Color.BLACK);
        jTable.setShowGrid(true);

        JScrollPane tPane = new JScrollPane(jTable);
        tPane.setMinimumSize(new Dimension(1000,1000));

        textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setColumns(50);
        textArea.setRows(1);
        JButton button = new JButton(actionManager.getOkButton());
        button.setText("OK");

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(150,130));
        panel.add(textArea, BorderLayout.WEST);
        panel.add(button, BorderLayout.EAST);

        JSplitPane panelSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, tPane);
        panelSplit.setDividerLocation(50);
        this.add(panelSplit);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showError(Error notification) {
        JOptionPane.showMessageDialog(this, notification.getMessage(), notification.getTitle(), JOptionPane.WARNING_MESSAGE);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;

    }
}