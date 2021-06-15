/**
 * CloseBtn Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseBtn extends JButton {
    CloseBtn(String text, Container cnt) {
        super(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cnt.setVisible(false);
            }
        });
    }
}
