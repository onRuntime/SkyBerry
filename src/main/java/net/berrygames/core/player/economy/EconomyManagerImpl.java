package net.berrygames.core.player.economy;

import net.berrygames.CallBack;
import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.managers.EconomyManager;
import net.berrygames.cloudberry.bukkit.player.PlayerData;
import net.berrygames.cloudberry.bukkit.player.economy.Multiplier;
import net.berrygames.core.player.PlayerDataImpl;

import java.util.UUID;

public class EconomyManagerImpl implements EconomyManager {

    @Override
    public CallBack<PlayerData> creditCoins(String playerName, long coins) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        playerData.setCoins(playerData.getCoins() + (coins));
        return success -> success.accept(playerData);
    }

    @Override
    public CallBack<PlayerData> creditCredits(String playerName, long credits) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        playerData.setCredits(playerData.getCredits() + (credits));
        return success -> success.accept(playerData);
    }

    @Override
    public Multiplier getMultiplier(String playerName) {
        return new MultiplierImpl(1, 0);
    }

    @Override
    public Multiplier getMultiplier(int type, int game) {
        return null;
    }
}
