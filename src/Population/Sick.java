/**
 * Sick class file, in Population package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Population;

import Country.Settlement;
import Location.Point;
import Simulation.Clock;
import Virus.IVirus;

/**
 * Sick class, inherits Person, represent a sick person
 */
public class Sick extends Person{
    private int contagiousTime;
    private final IVirus virus;

    /**
     * Constructor
     * @param age: the age of the Person
     * @param p1: the point where the person is (on the Map)
     * @param sett: the settlement where the Person lives
     * @param time: the time of contagion
     * @param v: the virus he was contagion with
     */
    public Sick(int age, Point p1, Settlement sett, int time, IVirus v) {
        super(age, p1, sett);
        this.contagiousTime = time;
        this.virus = v;
    }

    public Sick(Person p, int time, IVirus v) {
        super(p.getAge(), p.getLocation(), p.getSettlement());
        this.contagiousTime = time;
        this.virus = v;
    }

    /**
     * @return the virus the Person is contagion with
     */
    public IVirus getVirus() { return this.virus; }

    /**
     * calculates the probability of a person to be contagion
     * @return the probability of a person to be contagion
     */
    @Override
    double contagionProbability() {
        return 1;
    }

    /**
     * make a recovered version of that person
     * @return a Convalescent version of that person
     */
    public Person recover() {
        return new Convalescent(this.getAge(), this.getLocation(), this.getSettlement(), this.virus);
    }

    /**
     * simulate if the sick person will die or not
     * @return true if he did, false if he didn't
     */
    public boolean tryToDie() {
        return virus.tryToKill(this);
    }

    /**
     * @return the amount of days the person has been sick
     */
    public int daysBeingSick() {
        return Clock.calculateDays(contagiousTime);
    }

    public boolean tryToHeal() {
        return this.daysBeingSick() > 25;
    }
}
