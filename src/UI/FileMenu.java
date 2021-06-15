/**
 * FileMenu Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import Country.Map;
import IO.Caretaker;
import IO.Memento;
import Simulation.Clock;
import Simulation.Simulate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileMenu extends JMenu {

    FileMenu(String s) {
        super(s);
        setMinimumSize(new Dimension(40,20));
        setMaximumSize(new Dimension(40,20));

        load_MI = new JMenuItem("Load");
        statistics_MI = new JMenuItem("Statistics");
        statistics_MI.setEnabled(false);
        editMutations_MI = new JMenuItem("Edit Mutations");
        exit_MI = new JMenuItem("Exit");
        log_MI = new JMenuItem("Add log file");
        restoreLog_MI = new JMenuItem("Restore log file");

        load_MI.addActionListener(new ActionListener() {
            private Component dialog;
            @Override
            public void actionPerformed(ActionEvent e) {
                File file1;
                String path = "src/IO/input.txt";

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(path));
                chooser.setSelectedFile(new File(path));
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                if (chooser.showOpenDialog(dialog) != JFileChooser.OPEN_DIALOG) {
                    System.out.println("Loading from file failed");
                    return;
                }
                file1 = chooser.getSelectedFile();
                path = file1.getAbsolutePath();

                map = new CovidMap();
                MainWindow MW = MainWindow.getInstance();
                MW.addToRepaintList(map);
                MW.setMap(map);
                MW.add(map, BorderLayout.CENTER);
                MW.setVisible(true);// calls PaintComponent
                try {
                MW.simulate = new Simulate(path, MW.MW_Slider.getValue(), MW);

                }catch(InterruptedException err ){
                    err.printStackTrace();
                    return;
                }
                MW.MW_MenuBar.simulationMenu.stop_MI.setEnabled(true);
                MW.MW_MenuBar.simulationMenu.pause_MI.setEnabled(true);
                load_MI.setEnabled(false);

                Map countryMap = MW.simulate.getMap();
                //
                countryMap.setCyclicBarrier(countryMap.getSettlements().length , new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Repaint Panels");
                        if(Simulate.getIsRunning()) {
                            Clock.nextTick();
                            MW.repaintPanels();
                        } else {
                            synchronized (countryMap) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            Thread.sleep(1000 * Simulate.getSimulationSpeed());
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                countryMap.startThreads();
                statistics_MI.setEnabled(true);
            }
        });

        statistics_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsWindow();
            }
        });

//        editMutations_MI.addActionListener()
        editMutations_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MutationWindow();
            }
        });

        exit_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        log_MI.addActionListener(new ActionListener() {
            private Component dialog;
            @Override
            public void actionPerformed(ActionEvent e) {
                String curPath = "src/";
                JFileChooser jf = new JFileChooser();
                jf.setDialogTitle("Choose where do you want to save the file: ");
                jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jf.setCurrentDirectory(new File(curPath));

                int value = jf.showSaveDialog(MainWindow.getInstance());

                if (value == JFileChooser.APPROVE_OPTION) {
                    if (jf.getSelectedFile().isDirectory()) {
                        String filePath = jf.getSelectedFile().toString();

                        filePath = filePath + "/deadReport.txt";
                        File f = new File(filePath);
                        PrintWriter printWritrer = null;

                        try {
                            printWritrer = new PrintWriter(f);
                        } catch (FileNotFoundException err) {
                            err.printStackTrace();
                        }
                        printWritrer.close();
                        MainWindow.getInstance().logfileCaretaker.addMemento(new Memento(filePath));
                    } else {
                        JOptionPane.showMessageDialog(MainWindow.getInstance(),
                                "Please choose directory only!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        restoreLog_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Memento> mementoList = MainWindow.getInstance().getLogfileCaretaker().getStatesList();
                if(mementoList.size() >= 2) {
                    int index = mementoList.size() - 2;
                    mementoList.add(mementoList.get(index));
                    // the path that entered to the list before the current path will be the current path.
                }
                }
            }
    );

        add(load_MI);
        add(statistics_MI);
        add(editMutations_MI);
        add(exit_MI);
        add(log_MI);
        add(restoreLog_MI);
    }


    JMenuItem load_MI;
    JMenuItem statistics_MI;
    JMenuItem editMutations_MI;
    JMenuItem exit_MI;
    JMenuItem log_MI;
    JMenuItem restoreLog_MI;

    CovidMap map;
}