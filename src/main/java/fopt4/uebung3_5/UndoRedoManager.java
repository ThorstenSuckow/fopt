package fopt4.uebung3_5;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Notes: The RedoManager stores arbitrary UndoRedoActions.
 * In a real world application, it would probably be a better approach
 * to add a typed parameter so the application can distinguish between
 * UndoRedManagers, e.g, a Tree has the focus, undo/redo will only trigger
 * Tree-related actions, a ListView has a focus, only List-related actions
 * are undone/redone...
 */
public class UndoRedoManager {

    private List<UndoRedoAction> actions = new ArrayList<>();

    private SimpleBooleanProperty canUndoProperty = new SimpleBooleanProperty(false);

    private SimpleBooleanProperty canRedoProperty = new SimpleBooleanProperty(false);


    int position = -1;
    public void addAction(UndoRedoAction action) {

        for (int i = actions.size() - 1; i > position; i--) {
            actions.remove(actions.get(i));
        }

        actions.add(action);
        position++;
        updateBooleans();
    }

    private void updateBooleans() {
        canRedoProperty.set(position < actions.size() - 1);
        canUndoProperty.set(position > -1);
    }

    public SimpleBooleanProperty canUndoProperty() {
        return canUndoProperty;
    }

    public SimpleBooleanProperty canRedoProperty() {
        return canRedoProperty;
    }

    public boolean canUndo() {
        return canUndoProperty.get();
    }

    public boolean canRedo() {
        return canRedoProperty.get();
    }

    public void undo() {

        if (!canUndo()) {
            return;
        }

        actions.get(position--).undo();
        updateBooleans();
    }

    public void redo() {
        if (!canRedo()) {
            return;
        }

        actions.get(++position).redo();
        updateBooleans();
    }

}
