package net.berrygames.core.head;

import net.berrygames.cloudberry.bukkit.item.ItemBuilder;
import net.berrygames.cloudberry.bukkit.managers.HeadManager;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class HeadManagerImpl implements HeadManager {

    private Map<String, ItemBuilder> heads;

    public HeadManagerImpl() {
        heads = new HashMap<>();
    }

    @Override
    public void addHead(String name, String path) {
        var head = new ItemBuilder(Material.SKULL_ITEM);
        heads.put(name, head);
    }

    @Override
    public void removeHead(String name) {
        heads.remove(name);
    }

    @Override
    public ItemBuilder getHead(String name) {
        return heads.get(name);
    }
}
