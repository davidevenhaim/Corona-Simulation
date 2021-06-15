/**
 * Simulate Class file, in Simulation package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Simulation;

import Country.Settlement;
import Country.Map;
import Population.*;
import UI.MainWindow;
import Virus.*;
import IO.SimulationFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simulate class, responsible for the whole operation of the simulation
 */
public class Simulate {
    private Map map;
    static private boolean isRunning = true;
    static private boolean exit = false;
    static private int simulationSpeed;
    private MainWindow mw;

    /**
     *
     * @param path - the path to the file that will load the Settlements - names, position in map,
     *                 number of civilians.
     *  the file needs to match custom structure.
     * @param "isRunning - indicates if the map is simulating.
     * @parma simulationSpeed - user choice, indicates what is the simulation speed.
     *
     * @return the initialized map.
     */
    public Simulate(String path, int speed, MainWindow mw) throws InterruptedException {
        simulationSpeed = speed;
        map = loading(path);
        this.mw = mw;
        initializeMap(map);
    }

    public Map loading(String fileName) {
        SimulationFile file = new SimulationFile(fileName);
        Map map = new Map(this);
        file.loadMapFromFile(map);
        return map;
    }

    /**
     *
     * @param map - Map to be initialized.
     *  Initializing the map with 1% by making 1% of the population of every Settlement
     *  Sick with random variant, one of the 3 available.
     * @return the new Map after initialization.
     */
    static public void initializeMap(Map map) {
        Settlement[] settlements = map.getSettlements();
        ArrayList<Thread> tList = map.getThreadList();

        for(int i = 0; i < settlements.length; i++) {
            addSick(settlements[i]);
            tList.add(new Thread(new SimulateSettAdapter(settlements[i])));
        }
        map.setThreadList(tList);
    }

    static public synchronized void addSick(Settlement sett) {
        addSick(sett, 0.1);
    }

    static public synchronized void addSick(Settlement sett, double amount) {
        Random random = new Random();
        List<Person> healthyPeoples = sett.getHealthyPeople();
        List<Person> sickPeople = sett.getSickPeople();

        IVirus[] variantArr = { new ChineseVariant(), new BritishVariant(), new SouthAfricanVariant()};

        for(int j = 0; j < healthyPeoples.size() * amount; j++) {
            IVirus randomVirus = variantArr[random.nextInt(variantArr.length)];

            int index = random.nextInt(healthyPeoples.size());
            convertToSick(healthyPeoples.get(index), randomVirus, index, healthyPeoples, sickPeople);
        }

        sett.calculateRamzorGrade();
    }

    static public synchronized void convertToSick(Person person, IVirus virus, int index, List<Person> removeFrom,
                                                  List<Person> addTo) {
        if(person instanceof Sick) return;

        Sick sick = Contagion.contaige(person, virus);

        if(sick == null) {
            return;
        }
        removeFrom.remove(index);
        addTo.add(sick); // upcasting.
    }

    /**
     *
     * @param val = new is running value.
     */
    static public void setIsRunning(boolean val) {
        isRunning = val;
    }

    /**
     *
     * @return the current is running value.
     */
    static public boolean getIsRunning() { return isRunning; }
    /**
     *
     * @param newSimulationSpeed - user choose from the app slider.
     */
    static public void setSimulationSpeed(int newSimulationSpeed) {
        simulationSpeed = newSimulationSpeed;
    }

    /**
     *
     * @return the current simulation speed.
     */
    static public int getSimulationSpeed() { return simulationSpeed; }

    /**
     *
     * @param m - the new map value.
     */
//    public void setMap(Map m ) {
//        map = m;
//    }
    /**
     *
     * @return the current map.
     */
    public Map getMap() { return map; }
    /**
     *
     * @param val - new exit value
     */
    static public void setExit(boolean val) {
        exit = val;
    }
    /**
     *
     * @param map - Map to simulate
     *            Simulating the pandemia by looping through every settlement, picking "Sick" persons
     *              and trying to contagion 5 different random people from the same settlement.
     * @return - simulated map.
     */
    static public synchronized void simulateMap(Map map) {
        Clock.nextTick();
        Settlement[] settlements = map.getSettlements();

        for(int i = 0; i < settlements.length; i++) {
            tryToHealSickPeople(settlements[i]);
            tryToContagion(settlements[i]);
            tryToMovePeople(settlements[i]);

            settlements[i].setRamzorColor(settlements[i].calculateRamzorGrade());
        }
        map.setSettlements(settlements);
    }

    static public synchronized void simulateSett(Settlement sett) {
        tryToContagion(sett);
        tryToKill(sett);
        tryToMovePeople(sett);
        tryToVaccinate(sett);
        tryToHealSickPeople(sett);

        sett.setRamzorColor(sett.calculateRamzorGrade());
    }


    public static void tryToHealSickPeople(Settlement sett) {

        for(int j = 0; j < sett.getSickPeople().size(); j++) {
            Sick currentSick = (Sick) sett.getSickPeople().get(j);
            if(currentSick.tryToHeal()) {
                Person convalescent = currentSick.recover();
                sett.removePerson(sett.getSickPeople().get(j));
                sett.addPerson(convalescent);
            }
        }
    }


    public static synchronized void tryToContagion(Settlement sett, double amount) {
        List<Person> sickPeoples = sett.getSickPeople();
        List<Person> healthyPeople = sett.getHealthyPeople();
        List<Person> immunePeople = sett.getImmunePeople();
        Random random = new Random();

        for(int j = 0; j < sickPeoples.size() * amount; j++) {
            int randomSick = random.nextInt(sickPeoples.size());
            Sick currentSick = (Sick) sickPeoples.get(randomSick);

            for (int k = 0; k <= 3; k++) {
                if(healthyPeople.size() <= 0) break;
                int randomNumber = random.nextInt(healthyPeople.size() + immunePeople.size());

                Person current;
                if (randomNumber < healthyPeople.size() ) {
                    current = healthyPeople.get(randomNumber);
                    IVirus v = currentSick.getVirus();
                    if (v.tryToContagion(currentSick, current)) {
                        convertToSick(current,v, randomNumber, healthyPeople, sickPeoples);
                    }
                } else {
                    randomNumber -= healthyPeople.size();
                    current = immunePeople.get(randomNumber);
                    IVirus v = currentSick.getVirus();
                    if (v.tryToContagion(currentSick, current)) {
                        convertToSick(current, v, randomNumber, immunePeople, sickPeoples);
                    }
                }


            }
        }
    }

    public static synchronized void tryToContagion(Settlement sett) {
        tryToContagion(sett, 0.2);
    }

    public static synchronized void tryToMovePeople(Settlement sett) {
        tryToMovePeople(sett, 0.3);
    }

    public static synchronized void tryToMovePeople(Settlement sett, double amount) {
        List<Person> sickPeoples = sett.getSickPeople();
        List<Person> healthyPeople = sett.getHealthyPeople();
        List<Person> immunePeople = sett.getImmunePeople();
        Random random = new Random();

        for(int k = 0; k < sett.getCurrentPopulation() * amount; k++) {
            Person current = null;
            int randomArray = random.nextInt(sett.getCurrentPopulation());
            if(randomArray < healthyPeople.size()) {
                current = healthyPeople.get(random.nextInt(healthyPeople.size()));
            } else if (randomArray < healthyPeople.size() + sickPeoples.size()) {
                current = sickPeoples.get(random.nextInt(sickPeoples.size()));
            } else {
                if(immunePeople.size() > 0) {
                current = immunePeople.get(random.nextInt(immunePeople.size()));
                }
            }
            if(current != null) {
                Settlement randomSett = sett.getRandomNearbySettlement();
                if(current.tryToMove(randomSett)) {
                    sett.transferPerson(current, randomSett);
                }
            }
        }
    }

    public static synchronized void tryToVaccinate(Settlement sett) {
        Random random = new Random();

        for(int i = 0; i < sett.getHealthyAmount(); i++) {
            if(sett.getVaccineDosesAmount() > 0){
                int randomHealthy = random.nextInt(sett.getHealthyAmount());
                Person personToBeVaccinated = sett.getHealthyPeople().get(randomHealthy);
                Person vaccinated = personToBeVaccinated.vaccinate();
                if(vaccinated != null) { // if vaccination is completed successfully.
                sett.removePerson(personToBeVaccinated);
                sett.addPerson(vaccinated);
                }
                sett.decVaccineDosesAmount(); // decrement vaccine doses by 1.
            }
        }
    }

    public static synchronized void tryToKill(Settlement sett) {

        for(int j = 0; j < sett.getSickPeople().size(); j++) {
            Sick currentSick = (Sick) sett.getSickPeople().get(j);
            if(currentSick.tryToDie()) {
                sett.getSickPeople().remove(j);
                sett.incDeadPeopleAmount();
            }
        }
    }

}
