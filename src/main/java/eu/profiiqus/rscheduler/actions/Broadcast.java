package eu.profiiqus.rscheduler.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Broadcast is a wrapper for the server-execute command function
 *
 * @author Prof
 */
public class Broadcast implements Action {

    /**
     * Execute broadcasts a server-wide message
     * @param args
     */
    @Override
    public void execute(String args) {
        for(Player p: Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, args)));
        }
    }
}
