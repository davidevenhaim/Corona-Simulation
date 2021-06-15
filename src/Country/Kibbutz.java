/**
 * Kibbutz class file, in Country package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Country;

import Location.Location;
import Population.Person;

import java.util.List;

/**
 * Kibbutz class, inherit from settlement
 */
public class Kibbutz extends Settlement {

    /**
     * Constructor
     * @param name: the name of the Kibbutz
     * @param location: the Location of the Kibbutz
     * @param ramzorColor: the Ramzor Color of the Kibbutz
     */
    public Kibbutz(String name, Location location,  RamzorColor ramzorColor,  int curPopulation, int maxPopulation, Map map)
            throws InterruptedException {
        super(name, location, ramzorColor, curPopulation, maxPopulation, map);
    }

    /**
     * calculates the Ramzor Grade of the Kibbutz
     * @return the new Ramzor Grade
     */
    public RamzorColor calculateRamzorGrade() {
        double p = this.contagiousPercent();
        double rate = 0.45 + Math.pow(( Math.pow(1.5, this.getRamzorColor().rate) * (p - 0.4 )), 3);
        RamzorColor color = RamzorColor.calculateColor(rate);
        this.setRamzorColor(color);
        return color;
    }
}
