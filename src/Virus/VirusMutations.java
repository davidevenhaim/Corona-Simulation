/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Virus;

import java.util.ArrayList;
import java.util.Random;

public class VirusMutations {
    private static Boolean[][] mutationTable;
    private static IVirus[] viruses = { new BritishVariant(), new ChineseVariant(), new SouthAfricanVariant()};
    static {
        mutationTable = new Boolean[viruses.length][viruses.length];

        for(int i = 0; i < mutationTable.length; i++){
            for(int j = 0; j < mutationTable[i].length; j++) {
                mutationTable[i][j] = i == j;
            }
        }
    }

    public static Boolean[][] getInstance() { return mutationTable; }

    public static IVirus getMutation(IVirus virus) {
        ArrayList<IVirus> possibleMutation = new ArrayList<>();
        Random random = new Random();
        int column;

        if(virus instanceof BritishVariant) {
            column = 0;
        } else if( virus instanceof ChineseVariant) {
            column = 1;
        }else {
            column = 2;
        }

        for(int j = 0; j < viruses.length && j < mutationTable[column].length; j++) {
            if(mutationTable[column][j]) {
                possibleMutation.add(viruses[j]);
            }
        }
        if(possibleMutation.size() == 0) {
            return null;
        }

        int randomIndex = random.nextInt(possibleMutation.size());
        return possibleMutation.get(randomIndex);
    }
}
