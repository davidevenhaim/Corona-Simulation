/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Simulation;

import Country.Settlement;

import java.util.concurrent.BrokenBarrierException;

public class SimulateSettAdapter implements Runnable {
    private Settlement sett;

    public SimulateSettAdapter(Settlement sett) {
        this.sett = sett;
    }

    public void run() {
        sett.simulate();
    }
}
