/**
 * StatisticsWindow Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package UI;

import Country.Settlement;
import IO.StatisticsFile;
import Simulation.Simulate;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatisticsWindow extends JFrame {
    private Settlement[] sett;
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel bottomPanel;
    private JTable table;
    private String selectedSettlement;
    private int selectedRow;
    private MainWindow MW;

    public StatisticsWindow() {
        super("Statistics Window");
        this.MW = MainWindow.getInstance();
        sett = MW.simulate.getMap().getSettlements();

        Color mainColor = new Color(65,105,225);
        Color white = new Color(255, 255,255);

        this.setPreferredSize( new Dimension(800, 400));
        this.setBackground(mainColor);


        String[] colNames = {"Select Column", "Settlement Name", "Settlement Type", "Ramzor Color"};
        JComboBox tableColomuns = new JComboBox(colNames);
        tableColomuns.setBackground(white);
        tableColomuns.setPreferredSize(new Dimension(200, 40));
        tableColomuns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableColomuns.getSelectedIndex();
            }
        });

        JTextField tableTextFilter = new JTextField();
        tableTextFilter.setPreferredSize(new Dimension(150, 25));
        tableTextFilter.setBackground(white);
        tableTextFilter.setToolTipText("Settlements Filter");

        upperPanel = new JPanel();
        upperPanel.setBackground(mainColor);
        this.add(upperPanel, BorderLayout.NORTH);
        upperPanel.add(tableColomuns, BorderLayout.EAST);
        upperPanel.add(tableTextFilter, BorderLayout.WEST);

        String[] columnTableNames = new String[]{"Settlement Name", "Settlement Type", "Ramzor Color", "Number Of Sick",
                "Vaccine Doses", "Number Of Resident"};
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        for (String col : columnTableNames){
            tableModel.addColumn(col);
        }

        for (Settlement curSett : sett) {
            String settType = curSett.getClass().getName().split("\\.")[1];

            tableModel.addRow(new String[] {
                    curSett.getName(),
                    settType,
                    curSett.getRamzorColor().toString(),
                    String.valueOf(curSett.getSickAmount()),
                    String.valueOf(curSett.getVaccineDosesAmount()),
                    String.valueOf(curSett.getCurrentPopulation())
            });
        }

        mainPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (int i = 0; i < sett.length; i++) {
                    Settlement curSett = sett[i];
                    String settType = curSett.getClass().getName().split("\\.")[1];
                    String settDetails[] = {curSett.getName(),settType,curSett.getRamzorColor().toString(),
                            String.valueOf(curSett.getSickAmount()),String.valueOf(curSett.getVaccineDosesAmount()),
                            String.valueOf(curSett.getCurrentPopulation())};
                    for(int j = 0; j < settDetails.length; j++) {
                    tableModel.setValueAt(settDetails[j], i,j);
                    }
                }
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = table.getSelectedRow();
                int settlementsColumn = 0;
                selectedSettlement = table.getValueAt(selectedRow != -1 ? selectedRow : 0, settlementsColumn).toString();
            }
        });

        tableTextFilter.setToolTipText("Filter Settlement by Name");
        tableTextFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int colIndex = tableColomuns.getSelectedIndex();
                    if (colIndex != 0) {
                        sorter.setRowFilter(RowFilter.regexFilter(tableTextFilter.getText(), colIndex - 1 ));
                        table.setRowSorter(sorter);
                    }
                } catch (java.util.regex.PatternSyntaxException err) {
                    err.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    int columnIndex = tableColomuns.getSelectedIndex();
                    if(columnIndex != 0 ) {
                        sorter.setRowFilter(RowFilter.regexFilter(tableTextFilter.getText(), columnIndex));
                        table.setRowSorter(sorter);
                    }
                } catch(java.util.regex.PatternSyntaxException err) {
                    err.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    int columnIndex = tableColomuns.getSelectedIndex();
                    if(columnIndex != 0) {
                        sorter.setRowFilter(RowFilter.regexFilter(tableTextFilter.getText(), columnIndex));
                        table.setRowSorter(sorter);
                    }
                }catch(java.util.regex.PatternSyntaxException err) {
                    err.printStackTrace();
                }
            }
        });

        JButton saveBtn, vaccinateBtn, addSickBtn;

        bottomPanel = new JPanel();
        bottomPanel.setBackground(mainColor);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(saveBtn = new JButton("Save"));
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.fireTableDataChanged();
                String path = "src/IO/";
                StatisticsFile SF = new StatisticsFile(path);
                SF.saveStatisticsToFile(sett);

                JOptionPane.showMessageDialog(mainPanel, "Saved :)");
            }
        });
        bottomPanel.add(vaccinateBtn = new JButton("Vaccinate"));
        vaccinateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberOfVaccine = JOptionPane.showInputDialog("Enter number of Vaccine doses.");
                int vaccineDoses = Integer.parseInt(numberOfVaccine);
                if(vaccineDoses < 0 ) return;

                String settName = table.getValueAt(selectedRow != -1 ? selectedRow : 0, 0).toString();
                for(int i = 0; i < sett.length; i++) {
                    if(sett[i].getName() == settName) {
                        sett[i].addVaccineDosesAmount(vaccineDoses);
                        repaintList();
                        return;
                    }
                }

            }
        });
        bottomPanel.add(addSickBtn = new JButton("Add Sick"));
        addSickBtn.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String settName = table.getValueAt(selectedRow != -1 ? selectedRow : 0, 0).toString();
                for(int i = 0; i < sett.length; i++) {
                    if(sett[i].getName() == settName) {
                        Simulate.addSick(sett[i]);
                        repaintList();
                        return;
                    }
                }

            }
        }));
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.setSize(new Dimension(table.getSize()));
        this.pack();
        MW.addToRepaintList(mainPanel);
        this.setVisible(true);
    }

    void repaintList() {
        ArrayList<JPanel> repaintArray =  MW.getRepaintList();
        for(int i = 0; i < repaintArray.size(); i++) {
            repaintArray.get(i).repaint();
        }
    }

    public void setSelectedRow(int newRow) {
        selectedRow = newRow;
    }

}
