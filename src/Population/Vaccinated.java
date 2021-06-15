/**
 * Vaccinated class file, in Population package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Population;

import static java.lang.Math.sqrt;
import static java.lang.Math.min;
import static java.lang.Math.max;
import Country.Settlement;
import Location.Point;
import Simulation.Clock;

/**
 * Vaccinated class, inherits Person, represent a Vaccinated person
 */
public class Vaccinated extends Person{
    private long vaccinationTime;

    /**
     * Constructor
     * @param age: the age of the Person
     * @param p1: the point where the person is (on the Map)
     * @param sett: the settlement where the Person lives
     * @param time: the time of vaccination
     */
    public Vaccinated(int age, Point p1, Settlement sett, long time) {
        super(age, p1, sett);
        this.vaccinationTime = time;
    }

    /**
     * the method will calculate the probability of Vaccinated Person to be infected of one of the viruses,
     * depending on the days being vaccinated.
     *
     * @return double - the probability to get contagion.
     */
    @Override
    double contagionProbability() {
        long t = Clock.now() - this.vaccinationTime;
        if(t < 21) { return min(1, 0.56 + (0.15 * sqrt(21-t) )); }
        else { return max(0.05, 1.05 / (t - 14)); }
    }

}
