/**
 * RamzorColor enum file, in Country package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Country;

import java.awt.*;

/**
 * Ramzor Color enum.
 * each color has a decimel number that represent the maximal Ramzor-Grade a settlemnt can have to be colored in that color.
 */
public enum RamzorColor {
    Green (0.4, Color.GREEN, 1),
    Yellow (0.6, Color.YELLOW, 0.8),
    Orange (0.8, Color.ORANGE, 0.6),
    Red (1.0, Color.RED, 0.4);

    public final double rate;
    public final Color color;
    public final double passPossibility;

    /**
     * Constructor
     * @param rate: the maximum rate for a color
     * @param color: the Color object related to this color
     * @param passPossibility: the pass possibility for a settlement in this color
     */
    RamzorColor(double rate, Color color, double passPossibility) {
        this.rate = rate;
        this.color = color;
        this.passPossibility = passPossibility;
    }

    /**
     * calculate the Ramzor Color of a settlement based on its Ramzor Grade
     * @param rate: the Ramzor Grade of a Settlement
     * @return the Ramzor Color for that Grade
     */
    public static RamzorColor calculateColor(double rate) {
        if (rate <= Green.rate)
            return Green;
        else if (rate <= Yellow.rate)
            return Yellow;
        else if (rate <= Orange.rate)
            return Orange;
        else
            return Red;
    }
}
