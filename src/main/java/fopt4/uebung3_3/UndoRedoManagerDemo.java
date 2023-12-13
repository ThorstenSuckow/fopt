package fopt4.uebung3_3;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

interface UndoRedoAction {
    void undo();
    void redo();
}

class Command implements UndoRedoAction {
    SimpleProperty target;
    int value;
    public Command(SimpleProperty target, int value) {
        this.target = target;
        this.value = value;
    }

    public void undo() {
        System.out.println("[undo] " + target + "(" + (-value) + "))");
        this.target.add(-value, true);
    }

    @Override
    public void redo() {
        System.out.println("[redo] " + target + "(" + (value) + "))");
        this.target.add(value, true);
    }

}
class SimpleProperty {

    int value;

    UndoRedoManager manager;

    SimpleIntegerProperty prop;
    public SimpleProperty(int value, UndoRedoManager manager) {
        this.value = value;
        this.manager = manager;
    }

    public void add(int value, boolean silent) {
        if (!silent) {
            System.out.println("[add] " + this + "(" + value + ")");
            this.manager.track(new Command(this, value));
        }
        this.value += value;
    }

    public void add(int value) {
        this.add(value, false);
    }

    public String toString() {
        return String.valueOf(value);
    }

    public int get() {
        return value;
    }
}

class UndoRedoManager {

    List<UndoRedoAction> actions = new ArrayList<>();
    int position = -1;

    public void track(UndoRedoAction action) {
        // remove anything greater than the current action
        for (int i = actions.size() - 1; i > position; i--) {
            actions.remove(i);
        }
        // add the action and update position to the index
        // of the most recent action added
        actions.add(action);
        position++;
    }

    public void undo() {
        if (position == -1) {
            return;
        }
        System.out.print("[pos undo] " + position + " ");
        // undo current position, then shift left
        actions.get(position--).undo();
    }

    public void redo() {
        if (position == actions.size() - 1) {
            return;
        }

        System.out.print("[pos redo] " + position + " ");
        // shift right and redo this action
        actions.get(++position).redo();
    }
}


/**
 * Compile with vm-options "-ea" to enable assertions.
 */
public class UndoRedoManagerDemo {
    
    public static void main(String[] args) {

        UndoRedoManager manager = new UndoRedoManager();

        SimpleProperty prop = new SimpleProperty(5, manager);

        prop.add(4);
        assert(prop.get() == 9);

        prop.add(5);
        assert(prop.get() == 14);

        prop.add(6);
        assert(prop.get() == 20);

        manager.undo();
        assert(prop.get() == 14);

        manager.undo();
        assert(prop.get() == 9);

        manager.redo();
        assert(prop.get() == 14);

        manager.undo();
        assert(prop.get() == 9);

        prop.add(2);
        assert(prop.get() == 11);

        manager.redo();
        assert(prop.get() == 11);

        manager.redo();
        assert(prop.get() == 11);

        manager.undo();
        assert(prop.get() == 9);

        manager.undo();
        assert(prop.get() == 5);


    }


}
