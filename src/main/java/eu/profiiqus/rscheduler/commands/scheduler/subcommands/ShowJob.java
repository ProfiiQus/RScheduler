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

import java.util.ArrayList;

/**
 * Show job is a wrapper for scheduler's start subcommand
 *
 * @author Prof
 */
public class ShowJob implements Subcommand {

    FileConfiguration config;
    JobManager jobManager;

    /**
     * Show job constructor
     */
    public ShowJob() {
        this.config = ConfigurationManager.getInstance().getConfig();
        this.jobManager = JobManager.getInstance();
    }

    /**
     * Execute executes the job's stop functionality - shows the jobs details
     * @param sender The one who sent the command
     * @param args Command arguments
     */
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-show-usage")));
                return;
            }

            Job job = this.jobManager.getJob(args[1]);
            if(job == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-unknown")
                    .replace("{JOB}", args[1])));
                return;
            }

            for(String s: (ArrayList<String>) config.get("messages.job-show")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        s.replace("{NAME}", job.getId())
                        .replace("{RUNNING}", Boolean.toString(job.getRunning()))
                        .replace("{REPEATING}", job.getRepeatingSeconds() + "s")
                        .replace("{REMAINING}", job.getRemainingFormatted())));
            }
        } else {
            if(args.length == 1) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-show-usage")));
                return;
            }

            Job job = this.jobManager.getJob(args[1]);
            if(job == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-unknown").replace("{JOB}", args[1])));
                return;
            }

            for(String s: (ArrayList<String>) this.config.get("messages.job-show")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        s.replace("{NAME}", job.getId())
                                .replace("{RUNNING}", Boolean.toString(job.getRunning()))
                                .replace("{REPEATING}", job.getRepeatingSeconds() + "s")
                                .replace("{REMAINING}", job.getRemainingFormatted())));
            }
        }
    }
}
