package eu.profiiqus.rscheduler.actions;

import org.bukkit.Bukkit;

/**
 * Server command is a wrapper for the server-execute command function
 *
 * @author Prof
 */
public class ServerCommand implements Action {

    /**
     * Execute performs the action and executes command as the server
     * @param args
     */
    @Override
    public void execute(String args) {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), args);
    }
}
