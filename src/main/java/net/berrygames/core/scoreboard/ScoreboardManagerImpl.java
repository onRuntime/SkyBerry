package net.berrygames.core.scoreboard;

import net.berrygames.cloudberry.bukkit.managers.IScoreboardManager;
import net.berrygames.cloudberry.bukkit.scoreboard.NetworkScoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManagerImpl implements IScoreboardManager {

    private final Map<UUID, NetworkScoreboard> scoreboards = new HashMap<>();

    public ScoreboardManagerImpl() {}

    @Override
    public void addScoreboard(UUID uniqueId, NetworkScoreboard scoreboard) {
        scoreboards.put(uniqueId, scoreboard);
    }

    @Override
    public void removeScoreboard(UUID uniqueId) {
        scoreboards.get(uniqueId).stop();
        scoreboards.remove(uniqueId);
    }

    @Override
    public Map<UUID, NetworkScoreboard> getScoreboards() {
        return scoreboards;
    }
}
