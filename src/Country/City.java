/**
 * City class file, in Country package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Country;

import Location.Location;
import Population.Person;

import java.util.List;

/**
 * City class, inherit from settlement
 */
public class City extends Settlement {

    /**
     * Constructor
     * @param name: the name of the City
     * @param location: the Location of the city
     * @param people: the population of the City
     * @param ramzorColor: the Ramzor Color of the City
     */
    public City(String name, Location location, RamzorColor ramzorColor,
                int curPopulation, int maxPopulation, Map map) throws InterruptedException {
        super(name, location, ramzorColor, curPopulation, maxPopulation, map);
    }

    /**
     * calculates the Ramzor Grade of the City
     * @return the new Ramzor Grade
     */
    public RamzorColor calculateRamzorGrade() {
        double p = this.contagiousPercent();
        double c = 0.2 * Math.pow(4, 1.25 * p);
        RamzorColor color = RamzorColor.calculateColor(c);
        this.setRamzorColor(color);
        return color;
    }
}
