package eu.profiiqus.rscheduler.commands;

import eu.profiiqus.rscheduler.commands.scheduler.subcommands.ListJobs;
import eu.profiiqus.rscheduler.commands.scheduler.subcommands.ShowJob;
import eu.profiiqus.rscheduler.commands.scheduler.subcommands.StartJob;
import eu.profiiqus.rscheduler.commands.scheduler.subcommands.StopJob;
import eu.profiiqus.rscheduler.managers.ConfigurationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Scheduler command is the main command wrapper for all of the plugin's commands
 */
public class SchedulerCommand implements CommandExecutor {

    private HashMap<String, Subcommand> availableSubcommands;
    private FileConfiguration config;

    /**
     * Scheduler command constructor
     */
    public SchedulerCommand() {
        this.config = ConfigurationManager.getInstance().getConfig();
        this.availableSubcommands = new HashMap<String, Subcommand>() {
            {
                put("list", new ListJobs());
                put("start", new StartJob());
                put("stop", new StopJob());
                put("show", new ShowJob());
            }
        };
    }

    /**
     * On Command listens for the commands and decides on the usage of subcommands based on the first argument
     * @param sender The one who executed the command
     * @param command The command's name
     * @param label Some string?
     * @param args Commands arguments
     * @return Some boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("rscheduler.scheduler")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.not-enough-permissions")));
                return false;
            }
        }

        if(args.length == 0) {
            this.availableSubcommands.get("list").execute(sender, args);
            return false;
        }

        if(availableSubcommands.containsKey(args[0])) {
            this.availableSubcommands.get(args[0]).execute(sender, args);
        } else {
            this.availableSubcommands.get("list").execute(sender, args);
        }

        return false;
    }
}
