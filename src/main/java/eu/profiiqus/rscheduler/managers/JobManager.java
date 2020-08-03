package eu.profiiqus.rscheduler.managers;

import eu.profiiqus.rscheduler.RScheduler;
import eu.profiiqus.rscheduler.object.Job;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Job Manager class manages all the jobs currently in the system
 *
 * @author Prof
 */
public class JobManager {

    private HashMap<String, Job> jobs;
    private FileConfiguration config;

    private static JobManager instance;

    /**
     * Job Manager constructor
     */
    private JobManager() {
        this.config = ConfigurationManager.getInstance().getConfig();
        this.jobs = this.loadJobs();
        this.scheduleJobs();
    }

    /**
     * Get Instance returns singleton isntance fo Job Manager
     * @return Singleton instance of Job Manager
     */
    public static JobManager getInstance() {
        if(JobManager.instance == null) JobManager.instance = new JobManager();
        return JobManager.instance;
    }

    /**
     * Load jobs loads all jobs from the configuration file
     * @return Map of jobs from config
     */
    private HashMap<String, Job> loadJobs() {
        HashMap<String, Job> jobMap = new HashMap<>();

        String path;
        for(String key: this.config.getConfigurationSection("jobs").getKeys(false)) {
            path = "jobs." + key + ".";
            Job job = new Job(key, this.config.getInt(path + "repeating-time"), this.config.getLong(path + "remaining"), this.config.getBoolean(path + "running"), (ArrayList<String>) this.config.get(path + "action-list"));
            jobMap.put(key, job);
        }
        return jobMap;
    }

    /**
     * Save jobs saves all jobs into the configuration file
     */
    public void saveJobs() {
        String path;
        Job job;
        for(Map.Entry<String, Job> entry: this.jobs.entrySet()) {
            path = "jobs." + entry.getKey() + ".";
            job = entry.getValue();
            this.config.set(path + "repeating-time", job.getRepeatingSeconds());
            this.config.set(path + "remaining", job.getRemaining());
            this.config.set(path + "running", job.getRunning());
            this.config.set(path + "action-list", job.getActionList());
        }
        ConfigurationManager.getInstance().saveConfig();
    }

    /**
     * Get Jobs returns all jobs from memory
     * @return List of loaded jobs
     */
    public HashMap<String, Job> getJobs() {
        return this.jobs;
    }

    /**
     * Get Job returns job object from string id
     * @param id Id of the job
     * @return Job data object
     */
    public Job getJob(String id) {
        return this.jobs.get(id);
    }

    /**
     * Schedule jobs schedules jobs runs
     */
    private void scheduleJobs() {
        int timer = this.config.getInt("timer")*20;
        new BukkitRunnable() {
            public void run() {
                runJobs();
            }
        }.runTaskTimer(RScheduler.getPlugin(),0 , timer);
    }

    /**
     * Run jobs performs jobs action list or sets time
     */
    private void runJobs() {
        Job job;
        int timer = config.getInt("timer");
        for(Map.Entry<String, Job> entry: this.jobs.entrySet()) {
            job = entry.getValue();

            if(!job.getRunning())
                return;

            job.setRemaining(job.getRemaining() - (timer * 1000));

            if(job.getRemaining() <= 0) {
                job.performActions();
                job.setRemaining(job.getRepeatingSeconds()*1000);
            }
        }
    }
}
