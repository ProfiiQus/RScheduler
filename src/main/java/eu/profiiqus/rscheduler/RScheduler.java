package eu.profiiqus.rscheduler;

import eu.profiiqus.rscheduler.api.PlaceholderHook;
import eu.profiiqus.rscheduler.commands.SchedulerCommand;
import eu.profiiqus.rscheduler.managers.ConfigurationManager;
import eu.profiiqus.rscheduler.managers.JobManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * RScheduler class is the plugin's main class. It manages and initiates
 * all objects and managers and registers commands.
 *
 * @author Prof
 */
public final class RScheduler extends JavaPlugin {

    private static RScheduler plugin;
    private JobManager jobManager;
    private ConfigurationManager confManager;

    /**
     * On Enable method inits the plugin, it loads all managers
     * and registers commands and PAPI hooks.
     */
    @Override
    public void onEnable() {
        RScheduler.plugin = this;
        this.confManager = ConfigurationManager.getInstance();
        this.confManager.setup();
        this.jobManager = JobManager.getInstance();
        this.registerCommands();
        this.registerPAPI();
    }

    /**
     * On Disable method finishes the plugin's run, it saves all
     * jobs into the configuration file
     */
    @Override
    public void onDisable() {
        this.jobManager.saveJobs();
    }

    /**
     * Get Plugin method retrieves static instance of this plugin
     * @return The plugin itself
     */
    public static RScheduler getPlugin() {
        return RScheduler.plugin;
    }

    /**
     * Register command registers all spigot commands
     */
    private void registerCommands() {
        this.getCommand("scheduler").setExecutor(new SchedulerCommand());
    }

    /**
     * Register PAPI registers PlaceholderAPI hooks, if it's present
     */
    private void registerPAPI() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook(this).register();
        }
    }
}
