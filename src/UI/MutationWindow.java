/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package UI;

import Virus.VirusMutations;
import javax.swing.*;
import java.awt.*;

public class MutationWindow extends JDialog {
    public MutationWindow() {
        Color mainColor = new Color(65,105,225);
        Color white = new Color(255, 255,255);

        String[] variants = new String[]{"British Variant", "Chinese Variant", "SouthAfrican Variant"};

        BooleanTableModel booleanTable = new BooleanTableModel(variants, VirusMutations.getInstance());
        JTable table = new JTable(booleanTable);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        int windowHeight = 200;
        int windowWidth = 500;
        int height = (int) size.getHeight() / 2 - windowHeight;
        int width = (int) size.getWidth() / 2 - windowWidth;
        this.setLocation(height, width);

        this.setPreferredSize( new Dimension(windowWidth, windowHeight));
        this.setBackground(mainColor);
        this.add(new RowedTableScroll(table, variants));
        this.pack();
        this.setVisible(true);
    }

}
