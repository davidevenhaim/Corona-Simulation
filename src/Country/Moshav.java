/**
 * Moshav class file, in Country package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Country;

import Location.Location;
import Population.Person;

import java.util.List;

/**
 * Moshav class, inherit from settlement
 */
public class Moshav extends Settlement {

    /**
     * Constructor
     * @param name: the name of the Moshav
     * @param location: the Location of the Moshav
     * @param ramzorColor: the Ramzor Color of the Moshav
     */
    public Moshav(String name, Location location, RamzorColor ramzorColor, int curPopulation, int maxPopulation, Map map)
            throws InterruptedException {
        super(name, location, ramzorColor, curPopulation, maxPopulation, map);
    }

    /**
     * calculates the Ramzor Grade of the Moshav
     * @return the new Ramzor Grade
     */
    public RamzorColor calculateRamzorGrade() {
        double p = this.contagiousPercent();
        double rate = 0.3 + 3 * (Math.pow(( Math.pow(1.2, this.getRamzorColor().rate) * (p - 0.35 )), 5));
        RamzorColor color = RamzorColor.calculateColor(rate);
        this.setRamzorColor(color);
        return color;
    }
}
