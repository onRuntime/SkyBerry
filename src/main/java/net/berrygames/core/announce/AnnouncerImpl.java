package net.berrygames.core.announce;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.announce.Announcer;
import org.bukkit.plugin.Plugin;
import org.slf4j.event.Level;

public class AnnouncerImpl implements Announcer {

    private final Plugin plugin;

    public AnnouncerImpl() {
        this.plugin = BukkitCloud.get();
    }

    @Override
    public void send(final Level level, String announce) {
        //TODO: Make announces
        if(level == Level.WARN) {
        } else if (level == Level.INFO) {
        } else {
        }
        plugin.getServer().broadcastMessage(announce);
    }

    @Override
    public void sendNetwork(final Level level, String announce) {
        //TODO: Make Network announce
    }
}
