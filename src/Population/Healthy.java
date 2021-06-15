/**
 * Healthy class file, in Population package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Population;

import Country.Settlement;
import Location.Point;
import Simulation.Clock;

/**
 * Healthy class, inherits Person, represent a Healthy unvaccinated person
 */
public class Healthy extends Person {

    /**
     * Constructor
     * @param age: the age of the Person
     * @param p: the point where the person is (on the Map)
     * @param sett: the settlement where the Person lives
     */
    public Healthy(int age, Point p, Settlement sett) {
        super(age, p, sett);
    }

    /**
     * calculates the probability of a person to be contagion
     * @return the probability of a person to be contagion
     */
    @Override
    double contagionProbability() { return 1; }
}
