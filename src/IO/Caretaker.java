/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */

package IO;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> statesList = new ArrayList<Memento>();
    public void addMemento(Memento m) {
        statesList.add(m);
    }
    public Memento getMemento(int index) {
        return statesList.get(index);
    }
    public Memento getLastMemento() {
        if(this.statesList.size() > 0) {
        return statesList.get(statesList.size() - 1);
        } else {
            return null;
        }
    }

    public List<Memento> getStatesList() { return statesList;}
}
