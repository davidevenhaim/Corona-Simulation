/**
 * MainWindow Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import IO.Caretaker;
import Simulation.Simulate;
import Virus.VirusMutations;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame{
    private MainWindow() {
        super("Covid Map Simulator");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(1080, 720));
        setMaximumSize(new Dimension(1920, 1080));
        this.repaintList = new ArrayList<>();
        this.logfileCaretaker = new Caretaker();

        MW_MenuBar = new CovidMenuBar();
        add(MW_MenuBar, BorderLayout.NORTH);

        MW_Slider = new SimulationSpeedSlider(0, 1, 10, 10);
        MW_Slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(simulate != null)
                    simulate.setSimulationSpeed(MW_Slider.getValue());
            }
        });
        add(MW_Slider, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    static public MainWindow getInstance() {
        if(mainWindow == null) {
            mainWindow = new MainWindow();
            return mainWindow;
        }
        else {
            return mainWindow;
        }
    }

    public void addToRepaintList(JPanel repaintObject) {
        this.repaintList.add(repaintObject);
    }

    public ArrayList<JPanel> getRepaintList() { return this.repaintList; }

    public void repaintPanels() {
        for(int i = 0; i < repaintList.size(); i++) {
            repaintList.get(i).repaint();
        }
    }

    public Caretaker getLogfileCaretaker() { return this.logfileCaretaker; }

    public void setMap(CovidMap m) { map = m;}

    Thread t;
    CovidMenuBar MW_MenuBar;
    SimulationSpeedSlider MW_Slider;
    Simulate simulate;
    CovidMap map;
    ArrayList<JPanel> repaintList;
    Caretaker logfileCaretaker;
    static MainWindow mainWindow;
}

