package eu.profiiqus.rscheduler.commands;

import org.bukkit.command.CommandSender;

/**
 * Subcommand interface for creating sub commands
 */
public interface Subcommand {

    /**
     * Execute methods performs the commands functionality
     * @param sender The one who sent the command
     * @param args Command arguments
     */
    void execute(CommandSender sender, String[] args);
}
