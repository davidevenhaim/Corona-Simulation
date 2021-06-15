/**
 * Settlement class file, in Country package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Country;

import IO.Memento;
import Location.*;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import Simulation.Clock;
import Simulation.Simulate;
import UI.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

import static java.lang.Math.*;

/**
 * Settlement class, an abstract class that represent a generic settlement
 */
public abstract class Settlement {

    private String name;
    private Location location;
    private RamzorColor ramzorColor;
    private Map map;

    private List<Person> sickPeople;
    private List<Person> healthyPeople;
    private List<Person> immunePeople;
    private List<Settlement> nearbySettlements;

    private int currentPopulation;
    private int maxPopulation;
    private int vaccineDosesAmount;
    private int deadPeopleAmount;
    private double deadPeoplePercenatge;
    private double lastLog;

    /**
     * Constructor
     * @param name: the name of the settlement
     * @param location: the Location of the settlement
     * @param ramzorColor: the Ramzor Color of the settlement
     * @param currentPopulation: The current population of the settlement
     * @param maxPopulation: The maximum population of the settlement
     * @param map: current map that the settlement is located on.
     */
    Settlement(String name, Location location, RamzorColor ramzorColor,
               int currentPopulation, int maxPopulation, Map map) {
        this.name = name;
        this.location = location;
        this.ramzorColor = ramzorColor;
        this.currentPopulation = currentPopulation;
        this.maxPopulation = maxPopulation;
        this.map = map;

        this.vaccineDosesAmount = 0;
        this.deadPeopleAmount = 0;
        this.deadPeoplePercenatge = 0;
        this.lastLog = 0;

        this.sickPeople = new ArrayList<>();
        this.healthyPeople = new ArrayList<>();
        this.nearbySettlements = new ArrayList<>();
        this.immunePeople = new ArrayList<>();

        for(int i = 0; i < this.currentPopulation; i++) {
            healthyPeople.add( generateHealthyPerson(this, this.randomLocation()) );
        }
    }

    public void simulate() {
        while(Simulate.getIsRunning()) {
            Simulate.simulateSett(this);
            System.out.println("Simulating: " + name);

            this.deadPeoplePercenatge = (double) this.deadPeopleAmount / (double) this.currentPopulation;
            double currentPercent = Double.parseDouble(String.format("%.2f", deadPeoplePercenatge));

            if(this.lastLog < currentPercent) {
                this.writeToFile();
                this.lastLog = Double.parseDouble(String.format("%.2f", this.deadPeoplePercenatge));
            }
            try {
                map.getCyclicBarrier().await();
            } catch (InterruptedException e) {
                    e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the name of the settlement
     */
    public String getName() { return this.name; }

    /**
     * @return the Location of the settlement
     */
    public Location getLocation() { return this.location; }

    /**
     * @return the current population of the current settlement
     */
    public int getCurrentPopulation() {
        return this.healthyPeople.size() + this.sickPeople.size() + this.immunePeople.size(); }
    /**
     * @return the max population of the current settlement
     */
    public int getMaxPopulation() { return this.maxPopulation; }
    /**
     * @return the ramzor color of the settlement
     */
    public RamzorColor getRamzorColor() { return this.ramzorColor; }

    /**
     * set a new Ramzor Color for the settlement
     * @param c: the new Ramzor Color
     */
    public void setRamzorColor(RamzorColor c) { this.ramzorColor = c; }

    public void incDeadPeopleAmount() { this.deadPeopleAmount += 1;}

    public int getDeadPeopleAmount() { return this.deadPeopleAmount;}

    /**
     * @return the amount of sick people in the settlement
     */
    public int getSickAmount() { return this.sickPeople.size(); }

    public List<Person> getSickPeople() { return this.sickPeople;}

    public void setSickPeople(List<Person> newSickPeople) { this.sickPeople = new ArrayList<>(newSickPeople);}

    public void addNearbySettlement(Settlement sett) {
        this.nearbySettlements.add(sett);
    }

    public List<Settlement> getNearbySettlement() { return this.nearbySettlements;}


    public int getHealthyAmount() { return this.healthyPeople.size();}

    public List<Person> getHealthyPeople() {
        return this.healthyPeople;
    }

    public void setHealthyPeople(List<Person> newHealthyPeople) { this.healthyPeople = newHealthyPeople;}

    public List<Person> getImmunePeople() { return this.immunePeople; }

    public void setImmunePeople(List<Person> newImmunePeople) { this.immunePeople = newImmunePeople; }

    public int getVaccineDosesAmount() { return vaccineDosesAmount;}

    public void setVaccineDosesAmount(int newAmount) { vaccineDosesAmount = newAmount;}

    public Settlement getRandomNearbySettlement() {
        Random random = new Random();
        int randomSett = random.nextInt(this.nearbySettlements.size());
        return this.nearbySettlements.get(randomSett);
    }

    public void decVaccineDosesAmount() { vaccineDosesAmount -= 1;}

    public synchronized void addVaccineDosesAmount(int amount) { vaccineDosesAmount += amount;}
    /**
     * calculate the Ramzor grade of the settlement
     * the method is abstract, as each type of settlemnt has its own formula for calculating it
     * @return a double value that represnt the Ramzor grade of the settlement
     */
    public abstract RamzorColor calculateRamzorGrade();

    /**
     * calculates the percentage of the population who are sick
     * @return the percentage of the population who are sick
     */
    public double contagiousPercent() {
        return ((double)getSickAmount()/getCurrentPopulation());
    }

    /**
     * @return a random location in the settlement
     */
    public Point randomLocation() { return location.randomPointInLocation(); }

    /**
     *
     * @param person - person from another Settlement
     *               Method that will add person to the current settlement.
     *               Returns false if the person is currently in the current settlement.
     *
     * @return - Boolean - whether the transfer succeeded.
     */
    public boolean addPerson(Person person) {
        if(currentPopulation == maxPopulation) return false;

        if( person.getSettlement().getName().equals(this.name)) return false;

        if (person instanceof Sick) {
            this.sickPeople.add(person);
        } else if (person instanceof Healthy) {
            this.healthyPeople.add(person);
        } else {
            this.immunePeople.add(person);
        }
        return true;
    }
    /**
     *
     * @param person - person from the current settlement.
     *               The method will compare untill matching person is found in the List.
     *               If person is "Sick", sickAmount will be decreased.
     *
     * @return - Boolean - whether the removing succeeded.
     */
    public void removePerson(Person person) {
        if(person instanceof Sick) {
            for(int i = 0; i < this.sickPeople.size(); i++) {
                if(person == this.sickPeople.get(i)) {
                    this.sickPeople.remove(i);
                    break;
                }
            }
        } else if (person instanceof Healthy) {
            for(int i = 0; i < this.healthyPeople.size(); i++) {
                if(person == this.healthyPeople.get(i)) {
                    this.healthyPeople.remove(i);
                    break;
                }
            }
        } else {
            for(int i = 0; i < this.immunePeople.size(); i++) {
                if(person == this.immunePeople.get(i)) {
                    this.immunePeople.remove(i);
                    break;
                }
            }
        }
        setRamzorColor(calculateRamzorGrade());
    }

    /**
     *
     * @param person - person from the current settlement.
     * @param settlement - settlement to be transferred to.
     *                   if person is "Sick", the sick amount needs to be decreased.
     *  The ability to transfer person depends on two conditions:
     *                   - If settlement reached the max population, no more people can be added.
     *                   - The two settlements "ramzor" pass possibility multiplication.
     * @return Boolean - whether the transfer succeeded.
     */
    public boolean transferPerson(Person person, Settlement settlement) {
        int currentHashCode = System.identityHashCode(this);
        int otherHashCode = System.identityHashCode(settlement);
        if(currentHashCode <= otherHashCode) {
            synchronized (this) {
                synchronized (settlement) {
                    if(settlement.addPerson(person)) {
                        this.removePerson(person);
                        return true;
                    }
                }
            }
        } else {
            synchronized (settlement) {
                synchronized (this) {
                    if(settlement.addPerson(person)) {
                        // if adding the person to the target sett was successfull,
                        // only then remove the person from the current sett.
                        this.removePerson(person);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * creates a String representation of the Settlement
     * @return a String representation of the object
     */
    @Override
    public String toString() {
        return " Settlement Name: " + this.name + "\n" +
                "Located in: " + this.location.toString() + "\n" +
                "Currently with: " + this.getCurrentPopulation() + " civilians\n" +
                "And " + this.getSickAmount() + " of them are sick.\n" +
                "With contagious percent of: " + this.contagiousPercent() + "\n" +
                "Colored " + this.ramzorColor + "\n\n";
    }
    /**
     *
     * @param sett - settlement to be genereted to
     * @param point - represeting where the specific person will live.
     *
     * @return returning new healthy person.
     */
    Person generateHealthyPerson(Settlement sett, Point point) {
        Random random = new Random();

        int y = random.nextInt(5);
        int x = (int) Math.round(random.nextGaussian() * 6 + 9);
        int age = 5 * x + y;
        return new Healthy(age, point, sett);
    }

    public void writeToFile(){
        // update the file
        String temp;
        Memento memento = MainWindow.getInstance().getLogfileCaretaker().getLastMemento();
        if(memento != null) {
            String path = memento.getState();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            FileWriter fstream = null;
            BufferedWriter out = null;

            try {
                fstream = new FileWriter(path, true);
                out = new BufferedWriter(fstream);
                temp = "Time: " + dtf.format(now) + "\nSettlement name: " + this.getName() + "\nNumber of dead People: "
                        + this.deadPeopleAmount + "\nNumber of sick people: " + this.getSickPeople().size() + "\n\n";
                out.write(temp);
                out.close();
                fstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fstream != null) {
                    try {
                        fstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Memento is null.");
        }
    }

    //we need a function to fill a Person array with Person objects

}
