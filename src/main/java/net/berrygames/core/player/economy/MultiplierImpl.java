package net.berrygames.core.player.economy;

import net.berrygames.cloudberry.bukkit.player.economy.Multiplier;

import java.util.HashMap;
import java.util.Map;

public class MultiplierImpl implements Multiplier {

    private int amount;
    private final long endTime;

    private final Map<String, Integer> combinedData = new HashMap<>();

    private String message;

    public MultiplierImpl(int amount, long endTime) {
        this(amount, endTime, "");
    }

    public MultiplierImpl(int amount, long endTime, String message) {
        this.amount = amount;
        this.endTime = endTime;
        this.message = message;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Map<String, Integer> getCombinedData() {
        return combinedData;
    }

    @Override
    public Multiplier cross(int multiplier) {
        amount *= multiplier;
        combinedData.put("", multiplier);
        return this;
    }

    @Override
    public Multiplier cross(Multiplier multiplier) {
        amount *= multiplier.getAmount();
        combinedData.put(multiplier.getMessage(), multiplier.getAmount());
        return this;
    }

    @Override
    public boolean isValid() {
        return endTime > System.currentTimeMillis();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
