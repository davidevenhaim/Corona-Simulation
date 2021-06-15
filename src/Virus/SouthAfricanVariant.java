/**
 * SouthAfricanVariant class file, in Virus package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Virus;

import Population.Person;
import Population.Sick;
import Simulation.Clock;

import static java.lang.Math.*;

/**
 * SouthAfricanVariant class, the south african variant of the virus
 */
public class SouthAfricanVariant implements IVirus{

    /**
     * calculate the probability of a person to be contagion
     * @param p1: a Person object
     * @return the probability of that person to be contagion
     */
    public double contagionProbability(Person p1) {
        int age = p1.getAge();

        if(age <= 18) return 0.6;

        else return 0.5;
    }

    /**
     * calculate the probability of a person to die from the virus
     * @param p1: a Person object
     * @return the probability of that person to die
     */
    public double deathProbability(Person p1) {
        int age = p1.getAge();

        if(age <= 18) return 0.05;

        else return 0.08;
    }

    /**
     *
     * @param p1 - Person type - Sick person
     * @param p2 - Person type - Not a sick person
     *           The first person "p1" is sick in one of the virus variants,
     *           the method will calculate the probability of the second person "p2" to contagion.
     *           Depending on several parameters:
     *           	- Distance between
     *           	- Virus probability to contagion.
     *           If the second person is already "Sick" so the method will return false,
     *           sick person cannot get contagion again.
     * @return
     */
    public boolean tryToContagion(Person p1, Person p2) {
        if(p2 instanceof  Sick) { return false; }

        if(p1 instanceof Sick) {
            Sick s1 =(Sick) p1;
            if(s1.daysBeingSick() < 5) {
                return false;
            }
        }

        double d = p1.distanceFrom(p2);
        boolean isContagious = Math.random()  < min(1, 0.14 * pow(E, 2 - (0.25 * d) ));

        return isContagious;
    }

    /**
     *
     * @param s1 - Sick that got contagion in one of the viruses.
     *           the method will calculate the probability to be killed because of the virus
     *           depending on several parameters:
     *          	- Days being sick
     *          	- Virus type
     *          	- Age
     * @return
     */
    public boolean tryToKill(Sick s1) {
        long t = s1.daysBeingSick();

        double p = deathProbability(s1);

        return random() < max(0, (p - 0.01 * p * pow(t-15, 2) ));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SouthAfricanVariant) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "South African Variant";
    }
}
