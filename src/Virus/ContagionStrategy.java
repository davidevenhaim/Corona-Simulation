/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Virus;

import Population.Person;
import Population.Sick;

public interface ContagionStrategy {
    public Sick Mutate(Person p);
}
