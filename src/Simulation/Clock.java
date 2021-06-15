/**
 * Clock Class file, in Simulation package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Simulation;

/**
 * Clock class, the time is counted by "Ticks"
 */
public class Clock {
    static int time = 0;
    static int ticksPerDay = 1;

    /**
     * @return the current time
     */
    public static int now() { return time; }

    /**
     * increment the current time by one Tick
     */
    public static void nextTick() { time++; }
    /**
     * @return the current ticks per day!
     */
    public static int getTicksPerDay() { return ticksPerDay; }
    /**
     * @param newTicksPerDays - new value for ticks per day.
     */
    public static void setTicksPerDay(int newTicksPerDays) {
        ticksPerDay = newTicksPerDays;
    }
    /**
     *
     * @return the days that passed from the start of the simulation
     */
    public static int calculateDays() {
        return (int) Math.ceil(time / ticksPerDay);
    }

    public static int calculateDays(int someTime) {
        return (int) Math.ceil(time - someTime / ticksPerDay);
    }
}
