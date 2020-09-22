package net.berrygames.core.player.rank;

import net.berrygames.CallBack;
import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.managers.RankManager;
import net.berrygames.cloudberry.bukkit.player.PlayerData;
import net.berrygames.cloudberry.bukkit.player.rank.Rank;
import net.berrygames.core.player.PlayerDataImpl;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class RankManagerImpl implements RankManager {

    protected final Map<Integer, Rank> templates = new HashMap<>();

    public RankManagerImpl() {
        List<Rank> ranks = null;
        try {
            //ranks = BukkitCloud.get().getDatabaseService().getRanks();
            //TODO: Get ranks from RestAPI
            var i = 0;
            for (Rank rank : ranks) {
                templates.put(i, rank);
                i++;
            }
        } catch (Exception e) {
            // TODO: Proper error handling
        }
    }

    @Override
    public CallBack<PlayerData> applyRank(String playerName, int id, Date endDate) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        var rank = templates.get(id).clone();
        //TODO: Timed rank.
        playerData.addRank(rank);
        return success -> success.accept(playerData);
    }

    @Override
    public CallBack<PlayerData> applyRank(String playerName, int id) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        var rank = templates.get(id).clone();
        playerData.addRank(rank);
        return success -> success.accept(playerData);
    }

    @Override
    public CallBack<PlayerData> applyNextRank(String playerName) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        //TODO: Get next rank.
        return success -> success.accept(playerData);
    }

    @Override
    public CallBack<PlayerData> removeRank(String playerName, int id) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        playerData.getRanks().removeIf(rank -> rank.getName().equals(id));
        return success -> success.accept(playerData);
    }

    @Override
    public CallBack<PlayerData> removeLastRank(String playerName) {
        var playerData = (PlayerDataImpl) BukkitCloud.get().getPlayer(playerName);
        playerData.getRanks().remove(playerData.getRank());
        return success -> success.accept(playerData);
    }

    @Override
    public Rank getRank(String name) {
        AtomicReference<Rank> tmp = new AtomicReference<>();
        templates.forEach((i, rank)-> {
            if (rank.getName().equals(name)) {
                tmp.set(rank);
            }
        });
        return tmp.get();
    }

    @Override
    public Rank getRankByID(int id) {
        return templates.get(id);
    }

    @Override
    public Map<Integer, Rank> getRanks() {
        return templates;
    }
}
