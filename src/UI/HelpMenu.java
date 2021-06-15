/**
 * HelpMenu Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import Simulation.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu extends JMenu {

    HelpMenu(String s) {
        super(s);
        setMinimumSize(new Dimension(47,20));
        setMaximumSize(new Dimension(47,20));

        help_MI = new JMenuItem("Help");
        about_MI = new JMenuItem("About");

        help_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aboutUs = "- The program shows the state of the corona virus pandemic \r\n" +
                        "- Is made to indicate what settlements are safe to visit or not. \r\n" +
                        "- #Green color - Safe. #Yellow - Not so safe. #Orange - A little dangerous. #Red - Dangerous. \r\n" +
                        "- You can see pandemic statistics through the File --> Statistics \r\n" +
                        "- You can play/pause/stop the simulation through the Simulation Tab and set Ticks per day. \r\n" +
                        "- You can edit mutations via the File tab --> Edit Mutation category.  \r\n" +
                        "- See all as recommendation, software engineer Asaf Navon will not take any responsibility.";
                JOptionPane pane = new JOptionPane();
                pane.setMessage(aboutUs);
                JDialog dialog = pane.createDialog("About Us");
                dialog.setVisible(true);
            }
        });
        about_MI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aboutUs = "The writers of the program are: \r\n" +
                        "Asaf Navon - 207809997 \r\n" +
                        "David Livingstone - 208537019 \r\n" +
                        "The assignment was written in 4.5.2021";
                JOptionPane pane = new JOptionPane();
                pane.setMessage(aboutUs);
                JDialog dialog = pane.createDialog("About Us");
                dialog.setModal(false);
                dialog.setVisible(true);
            }
        });

        add(help_MI);
        add(about_MI);
    }

    JMenuItem help_MI;
    JMenuItem about_MI;
}
