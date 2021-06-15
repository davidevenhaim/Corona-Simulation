/**
 * SimulationMenu Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import Simulation.Clock;
import Simulation.Simulate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationMenu extends JMenu {

    SimulationMenu(String s) {
        super(s);
        setMinimumSize(new Dimension(90,20));
        setMaximumSize(new Dimension(90,20));

        play_MI = new JMenuItem("Play");
        play_MI.setEnabled(false);
        pause_MI = new JMenuItem("Pause");
        pause_MI.setEnabled(false);
        stop_MI = new JMenuItem("Stop");
        stop_MI.setEnabled(false);
        setTicksPerDay_MI = new JMenuItem("Set Ticks Per Day");

        play_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulate.setIsRunning(true);
                play_MI.setEnabled(false);
                pause_MI.setEnabled(true);
                synchronized (MainWindow.getInstance().simulate) {
                    MainWindow.getInstance().simulate.notify();
                }
                System.out.println("Playing Simulation.");
            }
        });

        pause_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulate.setIsRunning(false);
                play_MI.setEnabled(true);
                pause_MI.setEnabled(false);
                System.out.println("Pausing Simulation.");
            }
        });

        stop_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulate.setExit(true);
                MainWindow.getInstance().MW_MenuBar.fileMenu.load_MI.setEnabled(true);
                play_MI.setEnabled(false);
                pause_MI.setEnabled(false);
                stop_MI.setEnabled(false);
                System.out.println("Stoping Simulation.");
            }
        });

        setTicksPerDay_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(MainWindow.getInstance(), "Set ticks per day", true);
                SpinnerModel spinnerValue = new SpinnerNumberModel(Clock.getTicksPerDay(), 1, 24, 1);
                JSpinner spinner = new JSpinner(spinnerValue);
                CloseBtn btn = new CloseBtn("Confirm", dialog);
                spinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Clock.setTicksPerDay((int) spinner.getValue());
                    }
                });
                dialog.add(spinner, BorderLayout.NORTH);
                dialog.add(btn, BorderLayout.SOUTH);
                dialog.setPreferredSize(new Dimension(200, 100));
                dialog.pack();
                dialog.setLocation((MainWindow.getInstance().getWidth())/2 - dialog.getWidth()/2,
                        (MainWindow.getInstance().getHeight())/2 - dialog.getHeight()/2);
                dialog.setVisible(true);
            }
        });

        add(play_MI);
        add(pause_MI);
        add(stop_MI);
        add(setTicksPerDay_MI);
    }

    JMenuItem play_MI;
    JMenuItem pause_MI;
    JMenuItem stop_MI;
    JMenuItem setTicksPerDay_MI;

}
