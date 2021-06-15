/**
 * IVirus interface file, in Virus package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Virus;

import Location.*;
import Population.Person;
import Population.Sick;

/**
 * IVirus interface: all viruses implement this interface
 */
public interface IVirus {

	double contagionProbability(Person p1);

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
	boolean tryToContagion(Person p1, Person p2);

	/**
	 *
	 * @param s1 - Sick that got contagion in one of the viruses.
	 *           the method will calculate the probability to be killed because of the virus
	 *           depending on several parameters:
	 *          	- Days being sick
	 *          	- Virus type
	 *          	- Age
	 * @return - Boolean - the sick person is dead or not.
	 */
	boolean tryToKill(Sick s1);
}
