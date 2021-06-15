/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package UI;

import Country.Settlement;

import java.awt.*;

public class NearbySettlementLineDecorator {
    private Settlement sett1;
    private Settlement sett2;

    public NearbySettlementLineDecorator(Settlement sett1, Settlement sett2) {
        /**
         * constructor
         * @param Settlement sett1-the first settlement
         * @param Settlement sett2-the second settlement
         */
        this.sett1= sett1;
        this.sett2= sett2;
    }

    public void SetColor(Graphics g) {
        /**
         * set color
         * @param Graphics g-the component
         */
        Color color1 = sett1.getRamzorColor().color;
        Color color2 = sett2.getRamzorColor().color;
        int red_avg = (color1.getRed()+ color2.getRed())/2;
        int blue_avg = (color1.getBlue()+ color2.getBlue())/2;
        int green_avg = (color1.getGreen()+ color2.getGreen())/2;
        Color avg_color = new Color(red_avg, green_avg, blue_avg);
        g.setColor(avg_color);
    }

}
