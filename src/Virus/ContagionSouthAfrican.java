/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Virus;

import Population.Person;
import Population.Sick;
import Simulation.Clock;

public class ContagionSouthAfrican implements ContagionStrategy {
    @Override
    public Sick Mutate(Person p) {
        IVirus virus = VirusMutations.getMutation(new SouthAfricanVariant());
        if(virus == null) {
            return null;
        }
        return new Sick(p, Clock.now(), virus ); }
}
