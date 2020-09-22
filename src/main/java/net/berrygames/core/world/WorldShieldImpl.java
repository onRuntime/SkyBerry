package net.berrygames.core.world;

import net.berrygames.cloudberry.bukkit.world.WorldShield;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WorldShieldImpl implements WorldShield {

    private boolean pvp, pve, damage, foodLevel, blockBreak, blockPlace, drop, pick;

    public WorldShieldImpl() {
        enableAll();
    }

    @Override
    public void enableAll() {
        try {
            enableAll(true);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disableAll() {
        try {
            enableAll(false);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void enableAll(boolean enabled) throws InvocationTargetException, IllegalAccessException {
        for (Method declaredMethod : getClass().getDeclaredMethods()) {
            if(declaredMethod.getParameterCount() == 1 && declaredMethod.getParameterTypes()[0] == Boolean.class) {
                declaredMethod.invoke(this, enabled);
            }
        }
    }

    @Override
    public void enablePvP(boolean enabled) {
        pvp = enabled;
    }

    @Override
    public boolean isPvP() {
        return pvp;
    }

    @Override
    public void enablePvE(boolean enabled) {
        pve = enabled;
    }

    @Override
    public boolean isPvE() {
        return pve;
    }

    @Override
    public void enableDamage(boolean enabled) {
        damage = enabled;
    }

    @Override
    public void enableFoodLevelChange(boolean enabled) {
        foodLevel = enabled;
    }

    @Override
    public boolean isDamage() {
        return damage;
    }

    @Override
    public boolean isFoodLevelChange() {
        return foodLevel;
    }

    @Override
    public void enableBlockBreak(boolean enabled) {
        blockBreak = enabled;
    }

    @Override
    public void enableBlockPlace(boolean enabled) {
        blockPlace = enabled;
    }

    @Override
    public boolean isBlockBreak() {
        return blockBreak;
    }

    @Override
    public boolean isBlockPlace() {
        return blockPlace;
    }

    @Override
    public void enableDrop(boolean enabled) {
        drop = enabled;
    }

    @Override
    public boolean isDrop() {
        return drop;
    }

    @Override
    public void enabledPickup(boolean enabled) {
        pick = enabled;
    }

    @Override
    public boolean isPickup() {
        return pick;
    }
}
