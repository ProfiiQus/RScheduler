package eu.profiiqus.rscheduler.object;

import eu.profiiqus.rscheduler.executors.ActionExecutor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Job class is the object handler for each of the scheduler's job's data.
 * It can also perform all of it's actions from the action list
 *
 * @author Prof
 */
public class Job {

    private String id;
    private int repeatingSeconds;
    private long remaining;
    private boolean running;
    private List<String> actionList;

    public Job(String id, int repeatingSeconds, long remaining, boolean running, List<String> actionList) {
        this.id = id;
        this.repeatingSeconds = repeatingSeconds;
        this.remaining = remaining;
        this.running = running;
        this.actionList = actionList;
    }

    /**
     * Get Id returns job's id
     * @return Job's id as string
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get Repeating seconds returns the amount of seconds the job is repeating after
     * @return Amount of repeating seconds as int
     */
    public int getRepeatingSeconds() {
        return this.repeatingSeconds;
    }

    /**
     * Get Remaining returns amount of milliseconds remaining til the next run
     * @return Long amount of remaining milliseconds
     */
    public long getRemaining() {
        return this.remaining;
    }

    /**
     * Set Remaining sets the amount of remaining milliseconds
     * @param value The amount of milliseconds to set to
     */
    public void setRemaining(long value) {
        this.remaining = value;
    }

    /**
     * Get Action list returns list of actions to be performed when running
     * @return List of actions to be performed
     */
    public List<String> getActionList() {
        return this.actionList;
    }

    /**
     * Get Running returns boolean value on whether the job is running or not
     * @return Boolean value whether the job is running
     */
    public boolean getRunning() {
        return this.running;
    }

    /**
     * Set Running sets running property
     * @param value Value to set to
     */
    public void setRunning(boolean value) {
        this.running = value;
    }

    /**
     * Perform Actions performs all actions from the action list
     */
    public void performActions() {
        ActionExecutor action = new ActionExecutor(actionList);
        action.next();
    }

    /**
     * Get Remaining formatted returns formatted amount of time remaining
     * till the next performance of the action lsit
     * @return Formatted remaining time
     */
    public String getRemainingFormatted() {
        if(this.remaining > 86400000) {
            return TimeUnit.MILLISECONDS.toDays(this.remaining) + "d "
                    + (TimeUnit.MILLISECONDS.toHours(this.remaining) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(this.remaining))) + "h "
                    + (TimeUnit.MILLISECONDS.toMinutes(this.remaining) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.remaining))) + "m "
                    + (TimeUnit.MILLISECONDS.toSeconds(this.remaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.remaining))) + "s";
        }

        if(this.remaining > 3600000) {
            return TimeUnit.MILLISECONDS.toHours(this.remaining) + "h "
                    + (TimeUnit.MILLISECONDS.toMinutes(this.remaining) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.remaining))) + "m "
                    + (TimeUnit.MILLISECONDS.toSeconds(this.remaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.remaining))) + "s";
        }

        if(this.remaining > 60000) {
            return TimeUnit.MILLISECONDS.toMinutes(this.remaining) + "m "
                    + (TimeUnit.MILLISECONDS.toSeconds(this.remaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.remaining))) + "s";
        }

        return TimeUnit.MILLISECONDS.toSeconds(this.remaining) + "s";
    }
}
