/**
 * SimulationFile Class file, in IO package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package IO;

import Country.*;
import Location.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class SimulationFile, responsible for setting up the system from a .txt file
 * the .txt file should contain the data about the Settlements in the Country
 * eace settlement should have the following data in the following order with a ";" between them:
 * - the type of the Settlement (City/ Moshav/ Kibbutz)
 * - the x value of the top left corner on the map
 * - the y value of the top left corner on the map
 * - the width of the Settlement
 * - the height of the Settlement
 * - the population count
 * for example: "City; Ashdod; 0;0; 90;50; 10000"
 */
public class SimulationFile {
    private String filePath;

    /**
     * Constructor
     * @param filePath: the file's path
     */
    public SimulationFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @param map - Map to be filled based on filePath.
     *            the file should have a custom syntax that will allow
     *            to extract every settlement needed information.
     * @return
     */
    public boolean loadMapFromFile(Map map)  {
        FileReader fr;
        try { fr = new FileReader(filePath); }
        catch (FileNotFoundException e) {
            return false;
        }
        String[] SI; // Settlement Info
        String SN; //settlement name
        int locationX, locationY, locationH, locationW;
        int populationNumber, maxPopulation;
        Settlement sett = null;
        List<String> settlementsRoads = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(fr);
            String lineOfData = br.readLine();
            while(lineOfData != null) {
                lineOfData = lineOfData.replace(" ", "");
                SI = lineOfData.split(";");
                if(SI[0].equals("#") ) {
                    settlementsRoads.add(SI[1]);
                    settlementsRoads.add(SI[2]);
                } else {
                    SN = SI[1];
                    locationX = Integer.parseInt(SI[2]);
                    locationY = Integer.parseInt(SI[3]);
                    locationW = Integer.parseInt(SI[4]);
                    locationH = Integer.parseInt(SI[5]);
                    populationNumber = Integer.parseInt(SI[6]);
                    maxPopulation = (int)(populationNumber * 1.3);


                    sett = SettlementFactory.getSettlementinstance(SI[0], SN, new Location(locationX, locationY, locationH, locationW),
                            populationNumber, maxPopulation, map);

                    if(sett != null) {
                    map.addSettlement(sett);
                    }
                }
                lineOfData = br.readLine();

            }

            for(int i = 0; i < settlementsRoads.size(); i += 2) {
                map.connectSettlements(settlementsRoads.get(i), settlementsRoads.get(i+1));
            }

            br.close();
            fr.close();
        }
        catch (IOException e){return false;}
        return true;
    }

}
