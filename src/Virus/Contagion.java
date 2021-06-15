/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Virus;

import Population.Person;
import Population.Sick;

public class Contagion {
    private static Context context;

    public static Sick contaige(Person p, IVirus variant){

        if(variant.equals(new ChineseVariant()))
             context = new Context(new ContagionChinese());
        else if(variant.equals(new BritishVariant()))
            context = new Context(new ContagionBritish());
        else
            context = new Context(new ContagionSouthAfrican());

        return(context.executeStrategy(p));
    }
}
