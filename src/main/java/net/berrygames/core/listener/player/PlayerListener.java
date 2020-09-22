package net.berrygames.core.listener.player;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataLoadEvent;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataRefreshEvent;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataUnloadEvent;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataUpdateEvent;
import net.berrygames.cloudberry.bukkit.world.WorldShield;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {

    private final Plugin plugin;

    private final WorldShield worldShield;

    public PlayerListener() {
        this.plugin = BukkitCloud.get();
        worldShield = BukkitCloud.get().getWorldShield();
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            final var enabled = worldShield.isPvP();
            event.setCancelled(!enabled);
        } else {
            final var enabled = worldShield.isPvE();
            event.setCancelled(!enabled);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        final var enabled = worldShield.isDamage();
        event.setCancelled(!enabled);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        final var enabled = worldShield.isFoodLevelChange();
        event.setCancelled(!enabled);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        final var enabled = worldShield.isDrop();
        event.setCancelled(!enabled);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        final var enabled = worldShield.isPickup();
        event.setCancelled(!enabled);
    }

    @EventHandler
    public void onPlayerDataLoad(PlayerDataLoadEvent event) {
        final var playerData = event.getPlayerData();
        plugin.getLogger().info("PlayerData for " + playerData.getDisplayName() + " loaded in " + event.getLoadTime() + "ms");
    }

    @EventHandler
    public void onPlayerDataRefresh(PlayerDataRefreshEvent event) {
        final var playerData = event.getPlayerData();
        plugin.getLogger().info("PlayerData for " + playerData.getDisplayName() + " refreshed!");
    }

    @EventHandler
    public void onPlayerDataUpdate(PlayerDataUpdateEvent event) {
        final var playerData = event.getPlayerData();
        plugin.getLogger().info("PlayerData for " + playerData.getDisplayName() + " updated!");
    }

    @EventHandler
    public void onPlayerDataUnload(PlayerDataUnloadEvent event) {
        final var playerData = event.getPlayerData();
        plugin.getLogger().info("PlayerData for " + playerData.getDisplayName() + " unloaded!");
    }
}
