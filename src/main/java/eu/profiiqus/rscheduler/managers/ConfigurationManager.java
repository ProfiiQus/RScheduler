package eu.profiiqus.rscheduler.managers;

import eu.profiiqus.rscheduler.RScheduler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * ConfigurationManager class takes care about all configuration files and settings that are
 * happening in the plugin. It creates the files, loads the default values if they were not
 * present already and saves them. It also has methods to retrieve those configuration options.
 * Singleton design pattern is applied here.
 *
 * @author Prof
 */
public class ConfigurationManager {

    private File configFile;
    private FileConfiguration config;
    private RScheduler plugin;

    private static ConfigurationManager instance;

    private ConfigurationManager() {
        this.plugin = RScheduler.getPlugin();
    }

    /**
     * GetInstance method retrieves the ConfigurationManager singleton instance.
     * If it isn't initialized yet, creates a new one.
     * @return ConfigurationManager singleton instance
     */
    public static ConfigurationManager getInstance() {
        if(instance == null) instance = new ConfigurationManager();
        return instance;
    }

    /**
     * Setup method sets up all the configuration files, their parent directories,
     * saves the resources from plugin .jar and finally loads all the values into
     * configuration objects.
     */
    public void setup() {

        if(!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        this.configFile = new File(this.plugin.getDataFolder(), "config.yml");

        if(!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            this.plugin.saveResource("config.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void saveConfig() {
        try {
            this.config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GetConfig method retrieves plugin's main configuration file settings
     * @return Main configuration settings
     */
    public FileConfiguration getConfig() {
        return this.config;
    }
}