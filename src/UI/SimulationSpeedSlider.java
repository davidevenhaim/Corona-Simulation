/**
 * SimulationSpeedSlider Class file, in UI package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package UI;

import javax.swing.*;
import java.awt.*;

public class SimulationSpeedSlider extends JSlider {

    public SimulationSpeedSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
        Font font = new Font("Righteous", Font.ITALIC, 15);
        setFont(font);
        setEnabled(true);
        setMajorTickSpacing(1);
        setMinorTickSpacing(10);
        setPaintLabels(true);
        setPaintTicks(true);
    }
}
