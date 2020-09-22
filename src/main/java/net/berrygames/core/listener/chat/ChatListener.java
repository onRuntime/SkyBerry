package net.berrygames.core.listener.chat;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        var playerData = BukkitCloud.get().getPlayer(event.getPlayer().getName());
        var rank = playerData.getRank();

        String format = String.format("%1$s%2$s%3$s%4$s » ",
                formatColor(rank.getTag()),
                formatColor(rank.getPrefix()),
                playerData.getDisplayName(),
                formatColor(rank.getSuffix()));

        if(rank.getPermissionLevel() > 600) {
            format += formatColor(event.getMessage());
        } else {
            format += event.getMessage().replaceAll("§r", "");
        }
        event.setFormat(format.replace("%", "%%"));
    }

    private String formatColor(String message) {
        if(message == null || message.isEmpty()) return "";
        for (ChatColor color : ChatColor.values()) {
            message = message.replaceAll("(?i)&" + color.getChar(), "" + color);
        }
        return message;
    }
}
