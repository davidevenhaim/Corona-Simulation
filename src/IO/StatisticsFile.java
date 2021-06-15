/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package IO;

import Country.Settlement;

import java.io.*;

public class StatisticsFile {
    private String filePath;

    /**
     * Constructor
     * @param filePath: the file's path
     */
    public StatisticsFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @param settlements - settlements to be included in the statistics file
     * @return a boolean value that indicates if the process succeeded
     */
    public boolean saveStatisticsToFile(Settlement[] settlements) {
        File file = new File(filePath, "statistics.txt");
        FileWriter fw;

        if (!file.exists()) {
            try { file.createNewFile(); }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

            for(Settlement sett : settlements) { bw.write(getFormattedSettlement(sett)); }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getFormattedSettlement(Settlement sett){
        String settType = sett.getClass().getName().split("\\.")[1];
        String formattedSett = sett.getName()+","+settType+","+sett.getRamzorColor().toString()+","+
                String.valueOf(sett.getSickAmount())+","+String.valueOf(sett.getVaccineDosesAmount())+","+
                String.valueOf(sett.getCurrentPopulation())+"\n";
        return formattedSett;
    }
}


