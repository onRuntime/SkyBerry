package net.berrygames.core.listener.connection;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.core.player.PlayerDataImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        long loading = System.currentTimeMillis();

        BukkitCloud.get().getPlayerManager().loadPlayer(event.getPlayer().getName());
        //TODO: Load player permissions.

        //API.log(Level.INFO, event.getEventName() + " handled for " + event.getName() + " and loaded in " + (System.currentTimeMillis() - loading) + "ms");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");

        final var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(event.getPlayer().getName());
        if(playerData.hasNickname())
            playerData.applyNickname(event.getPlayer());

        //TODO: Apply player's permissions.
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");

        //TODO: Unloading Player's permissions
        BukkitCloud.get().getPlayerManager().unloadPlayer(event.getPlayer().getName());
        event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @EventHandler()
    public void onPlayerKick(PlayerKickEvent event) {
        event.setLeaveMessage("");
    }
}
