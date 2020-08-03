package eu.profiiqus.rscheduler.actions;

/**
 * Action interface for creating actions and command-pattern
 */
public interface Action {

    /**
     * Execute performs the action's functionality
     * @param args arguments
     */
    void execute(String args);
}
