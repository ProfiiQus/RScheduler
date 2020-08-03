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

/**
 * Stop Job is a wrapper for scheduler's start subcommand
 *
 * @author Prof
 */
public class StartJob implements Subcommand {

    private FileConfiguration config;
    private JobManager jobManager;

    /**
     * Start job constructor
     */
    public StartJob() {
        this.config = ConfigurationManager.getInstance().getConfig();
        this.jobManager = JobManager.getInstance();
    }

    /**
     * Execute executes the job's stop functionality - starts the jobs run
     * @param sender The one who sent the command
     * @param args Command arguments
     */
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-start-usage")));
                return;
            }

            Job job = this.jobManager.getJob(args[1]);
            if(job == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-unknown").replace("{JOB}", job.getId())));
                return;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-start-success").replace("{JOB}", job.getId())));
            job.setRunning(true);
        } else {
            if(args.length == 1) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-start-usage")));
                return;
            }

            Job job = this.jobManager.getJob(args[1]);
            if(job == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-unknown").replace("{JOB}", job.getId())));
                return;
            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("messages.job-start-success").replace("{JOB}", job.getId())));
            job.setRunning(true);
        }
    }
}
