package net.berrygames.core.listener.tablist;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.player.rank.Rank;
import net.berrygames.cloudberry.bukkit.scoreboard.TeamHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This file is a part of BerryGames, located on net.berrygames.core.listener.tablist
 *
 * @author Noctember
 * Created the 3/26/20 at 12:28 AM.
 */
public class TablistTeamManager {
    public static final char ESCAPE = '\u00A7';
    private final BukkitCloud api;
    private final TeamHandler teamHandler;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TablistTeamManager(BukkitCloud pl) {
        api = pl;

        teamHandler = new TeamHandler();
        List<Rank> tmpRank = new ArrayList();
        pl.getRankManager().getRanks().forEach((i, rank) -> tmpRank.add(rank));
        for (Rank rank : tmpRank) {
            if (rank == null)
                break;
            String teamName = rank.getName();

            if (teamHandler.getTeamByName(teamName) != null)
                continue;

            TeamHandler.VTeam vt = teamHandler.createNewTeam(teamName, "");

            vt.setRealName(getTeamName(teamName, rank.getOrder()));
            vt.setPrefix(parseColor(rank.getTag()));
            vt.setDisplayName(parseColor(rank.getTag()));
            vt.setSuffix(parseColor(rank.getSuffix()));

            teamHandler.addTeam(vt);
        }

        TeamHandler.VTeam npc = teamHandler.createNewTeam("NPC", "NPC");
        npc.setRealName("NPC");
        npc.setHideToOtherTeams(true);
        teamHandler.addTeam(npc);

    }

    private String getTeamName(String name, int rank) {
        String teamName = ((rank < 1000) ? "0" : "") +
                ((rank < 100) ? "0" : "") +
                ((rank < 10) ? "0" : "") +
                rank + name;
        return teamName.substring(0, Math.min(teamName.length(), 16));
    }

    public void playerLeave(final Player p) {
        executor.execute(() -> {
            teamHandler.removeReceiver(p);
        });
    }

    public void playerJoin(final Player p) {
        executor.execute(() -> {
            teamHandler.addReceiver(p);

            TeamHandler.VTeam teamByName = teamHandler.getTeamByName(api.getPlayer(p.getName()).getRank().getName());
            if (teamByName == null) {
                teamByName = teamHandler.getTeamByName("Player");
            }
            teamHandler.addPlayerToTeam(p, teamByName);

        });
    }

    private String parseColor(String value) {
        if (value == null)
            return "";
        value = value.replaceAll("&s", " ");
        value = org.bukkit.ChatColor.translateAlternateColorCodes('&', value);
        return value;
    }

    public TeamHandler getTeamHandler() {
        return teamHandler;
    }
}