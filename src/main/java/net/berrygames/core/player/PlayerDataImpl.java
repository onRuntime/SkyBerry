package net.berrygames.core.player;

import com.google.gson.Gson;
import lombok.Getter;
import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataRefreshEvent;
import net.berrygames.cloudberry.bukkit.event.player.PlayerDataUpdateEvent;
import net.berrygames.cloudberry.bukkit.player.PlayerData;
import net.berrygames.cloudberry.bukkit.player.economy.MoneyCallback;
import net.berrygames.cloudberry.bukkit.player.economy.Multiplier;
import net.berrygames.cloudberry.bukkit.player.permission.Permission;
import net.berrygames.cloudberry.bukkit.player.rank.Rank;
import net.berrygames.core.Core;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerDataImpl extends PlayerData {

    private Data data;

    private long lastRefresh = 0;

    public PlayerDataImpl(String playerName) {
        data = new Data(playerName);
        refreshData();
    }

    @Override
    public String getDisplayName() {
        return data.getPlayerName();
    }

    @Override
    public boolean hasNickname() {
        return data.getNickname() != null;
    }

    @Override
    public String getNickname() {
        if(!hasNickname()) return "";
        return data.getNickname();
    }

    public void applyNickname(Player player) {
        //TODO: Nickname application.
    }

    @Override
    public List<Rank> getRanks() {
        return data.ranks;
    }

    public void addRank(Rank rank) {
        data.getRanks().add(rank);
    }

    public void removeRank(Rank rank) {
        data.getRanks().remove(rank);
    }

    @Override
    public Rank getRank() {
        return getRanks().get(0);
    }

    @Override
    public int getPermissionLevel() {
        int allowed = 0;
        for (Rank rank : getRanks()) {
            allowed |= rank.getPermissionLevel();
        }
        return allowed;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return (getPermissionLevel() & permission.getOffset()) == permission.getOffset();
    }

    @Override
    public void withdrawCoins(long amount, MoneyCallback moneyCallback) {
        /*Bukk.get().getExecutor().execute(() -> {
            long result = decreaseCoins(amount);
            if (moneyCallback != null)
                moneyCallback.done(result, -amount, null);
        });*/
        //TODO: Withdraw coins
    }

    @Override
    public long decreaseCoins(long decrBy) {
        return increaseCoins(-decrBy);
    }

    @Override
    public long increaseCoins(long incrBy) {
        refreshData();
        //int result = (int) (bean.getCurrency().getCoins() + incrBy);
        //bean.getCurrency().setCoins(result);
        updateData();
        return 0;//result;
    }

    @Override
    public long increaseCredits(long incrBy) {
        refreshData();
        //int result = (int) (bean.getCurrency().getCredits() + incrBy);
        //bean.getCurrency().setCredits(result);
        updateData();
        return 0; //result;
    }

    public void setPermissionLevel(int permissionLevel) {
    }

    @Override
    public long getCoins() {
        return 0;
    }

    @Override
    public long getCredits() {
        return 0;
    }

    public void setCoins(long coins) {
    }

    @Override
    public void creditCoins(long amount, String reason, boolean applyMultiplier, MoneyCallback moneyCallback, boolean tell) {
        creditEconomy(0, amount, reason, applyMultiplier, moneyCallback, tell);
    }

    public void creditCredits(long amount, String reason, boolean applyMultiplier, MoneyCallback moneyCallback, boolean tell) {
        creditEconomy(1, amount, reason, applyMultiplier, moneyCallback, tell);
    }

    private void creditEconomy(int type, long amountFinal, String reason, boolean applyMultiplier, MoneyCallback moneyCallback, boolean tell) {
        int game = 0;
        /*Core.get().getExecutor().execute(() -> {
            try {
                long amount = amountFinal;
                String message = null;

                // TODO: Multiplier

                if (reason != null) {
                    message = getCreditMessage(amount, type, reason, new MultiplierImpl(1, 1));
                    // TODO: if (tell) sendmessage
                }

                long result = (type == 0) ? increaseCoins(amount) : (type == 1) ? increaseCredits(amount) : increaseCoins(amount);

                if (moneyCallback != null)
                    moneyCallback.done(result, amount, null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        //TODO: Credit economy
    }

    private String getCreditMessage(long amount, int type, String reason, Multiplier multiplier) {
        StringBuilder builder = new StringBuilder();
        builder.append(type == 0 ? ChatColor.GOLD + "+" + amount + " pièces (" + reason + ChatColor.GOLD + ")" : ChatColor.AQUA + "+" + amount + " étoiles (" + reason + ChatColor.AQUA + ")");

        if (multiplier != null) {
            for (String multCause : multiplier.getCombinedData().keySet()) {
                if (multCause == null || multiplier.getCombinedData().get(multCause) == 1)
                    continue;

                if (multCause.isEmpty()) {
                    builder.append(" [* " + multiplier.getCombinedData().get(multCause) + "]");
                } else {
                    builder.append(" [x").append(multiplier.getCombinedData().get(multCause)).append(" ").append(multCause).append("]");
                }
            }
        }

        return builder.toString();
    }

    public void setCredits(long credits) {
    }

    @Override
    public long getExperience() {
        return 0;
    }

    @Override
    public Locale getLocale() {
        return new Locale(null/*bean.getIdentity().getLocale()*/);
    }

    @Override
    public String format(String key, Object... parameters) {
        return BukkitCloud.get().getTranslatable().format(getDisplayName(), key, parameters);
    }

    @Override
    public boolean isLoaded() {
        return lastRefresh != 0;
    }

    @Override
    public boolean needRefresh() {
        return ((lastRefresh + 1000) * 60) < System.currentTimeMillis();
    }

    public void refreshData() {
        lastRefresh = System.currentTimeMillis();
        try {
            // - Get and hydrate player data.
            var newPlayer = ((Core) BukkitCloud.get()).getDataService().getPlayer(getDisplayName());
            var gson = new Gson();
            data = gson.fromJson(newPlayer, Data.class);

            // Fire player data refresh event.
            BukkitCloud.get().getServer().getPluginManager().callEvent(new PlayerDataRefreshEvent(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData() {
        if (isLoaded()) {
            try {
                var gson = new Gson();
                ((Core) BukkitCloud.get()).getDataService().updatePlayer(gson.toJson(data));

                // Fire player data update event.
                BukkitCloud.get().getServer().getPluginManager().callEvent(new PlayerDataUpdateEvent(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Data {
        @Getter
        String playerName, nickname;

        @Getter
        List<Rank> ranks;
        @Getter
        int permissionLevel;

        @Getter
        long coins, credits;

        @Getter
        long experience;

        @Getter
        Locale locale;

        public Data(String playerName) {
            this.playerName = playerName;
        }
    }
}
