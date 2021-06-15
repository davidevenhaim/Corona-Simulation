/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package IO;
/**
 * Memento class
 * the class responsible to create the memento
 */
public class Memento {
    private String state;
    public Memento(String state){
        /**
         * constructor for a memento
         * @param state is the path of the file.
         */
        this.state = state;
    }
    public String getState()
    {
        /**
         * get the actual state
         */
        return state;
    }

}
