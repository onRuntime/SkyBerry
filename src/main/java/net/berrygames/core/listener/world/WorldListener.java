package net.berrygames.core.listener.world;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.world.WorldShield;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class WorldListener implements Listener {

    private final WorldShield worldShield;

    public WorldListener() {
        worldShield = BukkitCloud.get().getWorldShield();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final var enabled = worldShield.isBlockBreak();
        event.setCancelled(!enabled);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final var enabled = worldShield.isBlockPlace();
        event.setCancelled(!enabled);
    }
}
