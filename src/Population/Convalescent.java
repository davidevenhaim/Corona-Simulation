/**
 * Convalescent class file, in Population package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Population;

import Country.Settlement;
import Location.Point;
import Virus.IVirus;

/**
 * Convalescent class, inherits Person, represent a Convalescent person
 */
public class Convalescent extends Person {
    // can be sick with 0.2 probability
    private IVirus virus;

    /**
     * Constructor
     * @param age: the age of the Person
     * @param p1: the point where the person is (on the Map)
     * @param sett: the settlement where the Person lives
     * @param v: the virus he has recovered from
     */
    public Convalescent(int age, Point p1, Settlement sett, IVirus v){
        super(age, p1, sett);
        this.virus = v;
    }

    /**
     * calculates the probability of a person to be contagion
     * @return the probability of a person to be contagion
     */
    @Override
    double contagionProbability() { return 0.2; }
}
