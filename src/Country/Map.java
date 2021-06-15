/**
 * Map class file, in Country package
 * authors:
* Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package Country;

import Simulation.Simulate;
import Simulation.SimulateSettAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CyclicBarrier;


public class Map implements Iterable {
    ArrayList<Thread> threadList;
    private Settlement[]  settlements;
    private Simulate simulation;
    private CyclicBarrier cyclicBarrier;

    /**
     * A default constructor
     */
    public Map(Simulate simulate) {
        this.settlements = new Settlement[0];
        this.threadList = new ArrayList<>();
        simulation = simulate;
    }
    /**
     * add a new Settlement to the Map
     * @param newSettlement: the new settlement to be added
     * @return true if the settlement was added, false if something went wrong
     */
    public boolean addSettlement(Settlement newSettlement) {
        //check that the settlements don't overlay
        int size = this.settlements.length;
        settlements = Arrays.copyOf(settlements, size + 1);
        settlements[size] = newSettlement;
        return true;
    }
    /***
     *
     * @return the current cyclic barrier
     */
    public CyclicBarrier getCyclicBarrier() { return this.cyclicBarrier;}
    /***
     *
     * @param num - the number of available threads.
     * @param run - the runnable method.
     */
    public synchronized void setCyclicBarrier(int num, Runnable run) {
        this.cyclicBarrier = new CyclicBarrier(num, run);
    }
    /***
     *
     * @return the current aviliable threads.
     */
    public ArrayList<Thread> getThreadList() { return this.threadList; }

    public void setThreadList(ArrayList<Thread> tList) {
        this.threadList = tList;
    }
    /***
     *  Starting all the threads - starting the simulation.
     */
    public void startThreads() {
        Iterator<Settlement> iterator = this.iterator();
        while(iterator.hasNext()) {
            new Thread(new SimulateSettAdapter(iterator.next())).start();
        }
    }
    /***
     * @return the array of Settlements in the map
     */
    public Settlement[] getSettlements() {
        return settlements;
    }

    public void setSettlements(Settlement[] sett) {
        this.settlements = sett;
    }
    /***
     * creates a String representation of the Map
     * @return a String representation of the object
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for(int i = 0; i< settlements.length; i++) {
            toString.append("Settlement[" + (i + 1) + "]" + settlements[i].toString());
        }
        return "" + toString;
    }

    public Settlement getSettByName(String settName) {
        for(int i = 0; i < this.settlements.length; i++) {
            if (this.settlements[i].getName() == settName) return this.settlements[i];
        }
        return null;
    }

    public Iterator<Settlement> iterator() {
        /**
         * @return the iterator behavior
         */
        return Arrays.stream(this.settlements).iterator() ;
    }

    public boolean connectSettlements(String settName1, String settName2) {
        int settIndex1 = -1, settIndex2 = -1;
        for(int i = 0; i < this.settlements.length; i++) {
            if(this.settlements[i].getName().equals(settName1)) {
                settIndex1 = i;
            }
            if(this.settlements[i].getName().equals(settName2)) {
                settIndex2 = i;
            }
        }

        if( settIndex1 != -1 && settIndex2 != -1) {
        this.settlements[settIndex1].addNearbySettlement(this.settlements[settIndex2]);
        this.settlements[settIndex2].addNearbySettlement(this.settlements[settIndex1]);

        return true;
        }
        return false;
    }

}
