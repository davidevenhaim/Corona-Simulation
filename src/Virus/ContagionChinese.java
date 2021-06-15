/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Virus;

import Population.Person;
import Population.Sick;
import Simulation.Clock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContagionChinese implements ContagionStrategy {
    @Override
    public Sick Mutate(Person p) {
        IVirus virus = VirusMutations.getMutation(new ChineseVariant());
        if(virus == null) {
            return null;
        }
        return new Sick(p, Clock.now(), virus ); }
}
