/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Virus;

import Population.Person;
import Population.Sick;

public class Context {
    private ContagionStrategy strategy;

    public Context(ContagionStrategy strategy){
        this.strategy = strategy;
    }

    public Sick executeStrategy(Person p){
        return strategy.Mutate(p);
    }
}