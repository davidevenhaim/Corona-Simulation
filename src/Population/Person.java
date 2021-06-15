/**
 * Person class file, in Population package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Population;

import Country.Settlement;
import Location.*;
import Simulation.Clock;
import Virus.IVirus;

/**
 * Person class, an abstract class that represent a generic Person
 */
public abstract class Person {
	private int age;
	private Point location;
	private Settlement settlement;

	/**
	 * Constructor
	 * @param age: the age of the Person
	 * @param p1: the point where the person is (on the Map)
	 * @param sett: the settlement where the Person lives
	 */
	public Person(int age, Point p1, Settlement sett) {
		this.age = age;
		this.location = p1;
		this.settlement = sett;
	}
	/**
	 * @return the Person's age
	 */
	public int getAge() { return this.age; }

	/**
	 * @return the Person's location
	 */
	public Point getLocation() { return this.location; }

	/**
	 * @return the Person's hometown
	 */
	public Settlement getSettlement() { return settlement; }

	/**
	 * measures the distance between the Person and another Person
	 * @param other: the other Peroson
	 * @return the distance between them
	 */
	public double distanceFrom(Person other) { return location.distance(other.location);}

	/**
	 * calculates the probability of a person to be contagion
	 * @return the probability of a person to be contagion
	 */
	abstract double contagionProbability();

	/**
	 * make a Sick version of the Person
	 * @param v: the virus to be contagion with
	 * @return a Sicl version of the Person
	 */
	public Person contagion(IVirus v) {
		return new Sick(this.age, this.location, this.settlement, Clock.now(), v );
	}

	public boolean tryToMove(Settlement destinationSett) {
		if(destinationSett.getCurrentPopulation() >= destinationSett.getMaxPopulation()) {
			return false;
		}
		Settlement currSett = this.getSettlement();

		return Math.random() <=
				currSett.getRamzorColor().passPossibility * destinationSett.getRamzorColor().passPossibility;
	}

	/**
	 * make a vaccinated version of the person only if the person current situation is healthy.
	 * @returna vaccinated version of the person
	 */
	public Person vaccinate() {
		if(this instanceof Healthy) {
			return new Vaccinated(this.getAge(), this.getLocation(), this.getSettlement(), Clock.now());
		} else {
			return null;
		}
	}
}
