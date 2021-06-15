/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Country;

import Location.Location;

public class SettlementFactory {
    static Settlement sett;

    static public Settlement getSettlementinstance(String settType,String settName,Location location,int population,int capacity,
                                            Map map) {
        /***
         * settType: settlement type
         * settName: settlement name
         * location: Location class instance of the settlement
         * population
         * capacity: max Population for the settlement
         * map: map that the sett will belong to
         */
        switch(settType) {
            case "Moshav":
                try {
                    sett = new Moshav(settName, location,
                            RamzorColor.Green, population, capacity, map);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "Kibbutz":
                try {
                    sett = new Kibbutz(settName, location,
                            RamzorColor.Green, population, capacity, map);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    sett = new City(settName, location,
                            RamzorColor.Green, population, capacity, map);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
        return sett;
    }
}
