package net.berrygames.core.player;

import net.berrygames.cloudberry.bukkit.event.player.PlayerDataLoadEvent;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataUnloadEvent;
import net.berrygames.cloudberry.bukkit.managers.PlayerManager;
import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.managers.EconomyManager;
import net.berrygames.cloudberry.bukkit.player.PlayerData;
import net.berrygames.core.player.economy.EconomyManagerImpl;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManagerImpl implements PlayerManager {

    protected final ConcurrentHashMap<String, PlayerData> cache = new ConcurrentHashMap<>();
    private final EconomyManager economyManager;

    private final Plugin plugin;

    public PlayerManagerImpl() {
        this.plugin = BukkitCloud.get();
        economyManager = new EconomyManagerImpl();
    }

    @Override
    public void loadPlayer(String playerName) {
        var loadTime = System.currentTimeMillis();
        var playerData = new PlayerDataImpl(playerName);
        cache.put(playerName, playerData);
        loadTime = System.currentTimeMillis() / loadTime;
        // Fire player data load event.
        plugin.getServer().getPluginManager().callEvent(new PlayerDataLoadEvent(playerData, loadTime));
    }

    @Override
    public void unloadPlayer(String playerName) {
        if(cache.containsKey(playerName)) {
            ((PlayerDataImpl) cache.get(playerName)).updateData();
            // Fire player data unload event.
            plugin.getServer().getPluginManager().callEvent(new PlayerDataUnloadEvent(cache.get(playerName)));
        }
        cache.remove(playerName);
    }

    @Override
    public PlayerData getPlayer(String playerName) {
        return getPlayer(playerName, false);
    }

    @Override
    public PlayerData getPlayer(String playerName, boolean forceRefresh) {
        if(playerName == null || playerName.isEmpty())
            throw new NullPointerException("Player name is null!");
        if(cache.containsKey(playerName)) {
            loadPlayer(playerName);
        }

        var playerData = cache.get(playerName);
        if(playerData == null) return getPlayer(playerName, forceRefresh);

        if(forceRefresh || playerData.needRefresh()) {
            ((PlayerDataImpl) cache.get(playerName)).refreshData();
            return playerData;
        }
        return playerData;
    }

    @Override
    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    @Override
    public void connect(String playerName, String server) {
        //TODO: PubSub connection message.
    }

    @Override
    public void kick(String playerName, TextComponent reason) {
    }
}
