package eu.profiiqus.rscheduler.api;

import eu.profiiqus.rscheduler.RScheduler;
import eu.profiiqus.rscheduler.managers.JobManager;
import eu.profiiqus.rscheduler.object.Job;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * PlaceholderHook class hooks into placeholder API with new rscheduler placeholders
 */
public class PlaceholderHook extends PlaceholderExpansion {

    private RScheduler plugin;
    private JobManager jobManager;

    /**
     * Placeholder hook constructor
     * @param plugin The plugin's instance
     */
    public PlaceholderHook(RScheduler plugin) {
        this.plugin = plugin;
        this.jobManager = JobManager.getInstance();
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "rscheduler";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        Job job = jobManager.getJob(identifier);
        if(job != null) {
            return job.getRemainingFormatted();
        }

        return null;
    }
}
