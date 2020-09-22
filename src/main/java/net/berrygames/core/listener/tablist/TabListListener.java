package net.berrygames.core.listener.tablist;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.player.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TabListListener implements Listener {

    private final TablistTeamManager manager;

    public TabListListener() {
        manager = new TablistTeamManager(BukkitCloud.get());

        /*BukkitCloud.get().getNPCManager().setScorebordRigister((data, err) -> {
            TeamHandler.VTeam npc = manager.getTeamHandler().getTeamByName("NPC");
            if (npc != null)
                manager.getTeamHandler().addPlayerToTeam(data.getName(), npc);
        });*/
    }

    private String formatColor(String message) {
        String tmp = message;
        for (ChatColor color : ChatColor.values()) {
            tmp = tmp.replaceAll("(?i)&" + color.getChar(), "" + color);
        }
        return tmp;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final var playerData = BukkitCloud.get().getPlayer(player.getName());
        manager.playerJoin(player);
        Bukkit.getScheduler().runTaskAsynchronously(BukkitCloud.get(), () -> {
            Rank rank = playerData.getRank();
            final String displayName = String.format("%1$s%2$s%3$s%4$s",
                    formatColor(rank.getTag()),
                    formatColor(rank.getPrefix()),
                    playerData.getDisplayName(),
                    formatColor(rank.getSuffix()));
            player.setDisplayName(displayName);
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        manager.playerLeave(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        manager.playerLeave(event.getPlayer());
    }
}
