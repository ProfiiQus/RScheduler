package eu.profiiqus.rscheduler.executors;

import eu.profiiqus.rscheduler.RScheduler;
import eu.profiiqus.rscheduler.actions.Broadcast;
import eu.profiiqus.rscheduler.actions.ServerCommand;
import eu.profiiqus.rscheduler.actions.Action;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

/**
 * Action executor is a manager for executing action lists
 *
 * @author Prof
 */
public class ActionExecutor {

    private List<String> actionList;
    private int index;

    private static HashMap<String, Action> availableActions = new HashMap<String, Action>() {
        {
            put("[broadcast]", new Broadcast());
            put("[command]", new ServerCommand());
        }
    };

    /**
     * Action executor constructor
     * @param actionList List of actions
     */
    public ActionExecutor(List<String> actionList) {
        this.actionList = actionList;
        this.index = 0;
    }

    /**
     * Move to next action in the list and perform it
     */
    public void next() {

        if(this.index == this.actionList.size())
            return;

        String[] actionArgs = this.actionList.get(this.index).split(" ", 2);
        this.index++;

        if(actionArgs[0].equals("[delay]")) {
            new BukkitRunnable() {
                public void run() {
                    next();
                }
            }.runTaskLater(RScheduler.getPlugin(), Integer.parseInt(actionArgs[1])*20);
        } else {
            this.availableActions.get(actionArgs[0]).execute(actionArgs[1]);
            this.next();
        }
    }
}
