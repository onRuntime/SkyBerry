package net.berrygames.core.title;

import net.berrygames.cloudberry.bukkit.title.AnimatedTitle;
import net.berrygames.cloudberry.bukkit.managers.TitleManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TitleManagerImpl implements TitleManager {

    protected final Map<UUID, List<AnimatedTitle>> titles = new HashMap<>();

    @Override
    public void addTitle(UUID uniqueId, AnimatedTitle title) {
        if(!titles.containsKey(uniqueId)) {
            titles.put(uniqueId, new ArrayList<>());
            new BukkitRunnable() {
                @Override
                public void run() {
                    final var text = title.get(title.getTime())[0];
                    final var subtitle = title.get(title.getTime())[1];

                    //player.sendTitle(title, subtitle, title.getFadeIn(), title.getStay(), title.getFadeOut());
                    title.setTime(title.getTime() + 1);
                    if(title.getTime() >= Collections.max(title.keySet()))
                        cancel();
                }
            };
        }
        titles.get(uniqueId).add(title);
    }
}
