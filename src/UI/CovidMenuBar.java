/**
 * CovidMenuBar Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import Country.Map;
import Simulation.Clock;
import Simulation.Simulate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class CovidMenuBar extends JMenuBar {

    public CovidMenuBar() {
        setMinimumSize(new Dimension(200,20));
        setMaximumSize(new Dimension(9999,20));

        fileMenu = new FileMenu("File");
        add(fileMenu);


        simulationMenu = new SimulationMenu("Simulation");

        add(simulationMenu);


        helpMenu = new HelpMenu("Help");

        add(helpMenu);
    }

    FileMenu fileMenu;
    SimulationMenu simulationMenu;
    HelpMenu helpMenu;
}
