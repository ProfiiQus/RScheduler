package eu.profiiqus.rscheduler.commands.scheduler.subcommands;

import eu.profiiqus.rscheduler.commands.Subcommand;
import eu.profiiqus.rscheduler.managers.ConfigurationManager;
import eu.profiiqus.rscheduler.managers.JobManager;
import eu.profiiqus.rscheduler.object.Job;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * List Jobs is a wrapper for scheduler's start subcommand
 *
 * @author Prof
 */
public class ListJobs implements Subcommand {

    private FileConfiguration config;
    private JobManager jobManager;

    /**
     * List jobs constructor
     */
    public ListJobs() {
        this.config = ConfigurationManager.getInstance().getConfig();
        this.jobManager = JobManager.getInstance();
    }

    /**
     * Execute executes the job's stop functionality - lists the jobs
     * @param sender The one who sent the command
     * @param args Command arguments
     */
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-list").replace("{JOBS}", generateJobList())));
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-list").replace("{JOBS}", generateJobList())));
        }
    }

    private String generateJobList() {
        String result = "";
        HashMap<String, Job> jobList = this.jobManager.getJobs();
        Job job;
        for(Map.Entry<String, Job> entry: jobList.entrySet()) {
            job = entry.getValue();
            if(job.getRunning()) {
                result += ChatColor.GREEN + entry.getKey();
            } else {
                result += ChatColor.RED + entry.getKey();
            }
            result += ",";
        }
        return result.substring(0, result.length()-1);
    }
}
