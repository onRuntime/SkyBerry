package net.berrygames.core.party;

import net.berrygames.cloudberry.bukkit.party.Party;

import java.util.List;
import java.util.UUID;

public class PartyImpl implements Party {

    private UUID owner;

    private List<UUID> members;

    public PartyImpl(UUID owner) {
        this.owner = owner;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public boolean isOwner(UUID uniqueId) {
        return owner == uniqueId;
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }

    @Override
    public List<UUID> getOnlineMembers() {
        return null;
    }

    @Override
    public void addMember(UUID uniqueId) {
        members.add(uniqueId);
    }

    @Override
    public void removeMember(UUID uniqueId) {
        members.remove(uniqueId);
    }

    @Override
    public void delete() {
        //TODO: Method.
    }
}
