package net.berrygames.core;

import net.berrygames.cloudberry.bukkit.BukkitCloud;
import net.berrygames.cloudberry.bukkit.announce.Announcer;
import net.berrygames.cloudberry.bukkit.managers.*;
import net.berrygames.cloudberry.bukkit.world.WorldShield;
import net.berrygames.cloudberry.bukkit.managers.PlayerManager;
import net.berrygames.cloudberry.bukkit.managers.RankManager;
import net.berrygames.cloudberry.data.DataService;
import net.berrygames.core.announce.AnnouncerImpl;
import net.berrygames.core.data.DataServiceImpl;
import net.berrygames.core.head.HeadManagerImpl;
import net.berrygames.core.inventory.InventoryManagerImpl;
import net.berrygames.core.player.PlayerManagerImpl;
import net.berrygames.core.player.rank.RankManagerImpl;
import net.berrygames.core.listener.chat.ChatListener;
import net.berrygames.core.listener.connection.ConnectionListener;
import net.berrygames.core.listener.player.PlayerListener;
import net.berrygames.core.listener.tablist.TabListListener;
import net.berrygames.core.listener.world.WorldListener;
import net.berrygames.core.scoreboard.ScoreboardManagerImpl;
import net.berrygames.core.title.TitleManagerImpl;
import net.berrygames.translation.Translatable;
import net.berrygames.core.world.WorldShieldImpl;
import net.berrygames.translation.ITranslatable;

public class Core extends BukkitCloud {

    // - Bukkit Manager
    private IScoreboardManager scoreboardManager;
    private InventoryManager inventoryManager;
    private TitleManager titleManager;
    private PlayerManager playerManager;
    private HeadManager headManager;
    private WorldShield worldShield;
    private Announcer announcer;

    // - General Managers
    private RankManager rankManager;
    private ITranslatable translatable;

    // - Internal
    private DataService dataService;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // - Initialize bukkit managers.
        this.scoreboardManager = new ScoreboardManagerImpl();
        this.inventoryManager = new InventoryManagerImpl();
        this.titleManager = new TitleManagerImpl();
        this.playerManager = new PlayerManagerImpl();
        this.headManager = new HeadManagerImpl();
        this.worldShield = new WorldShieldImpl();
        this.announcer = new AnnouncerImpl();

        // - Initialize general managers.
        this.rankManager = new RankManagerImpl();
        this.translatable = new Translatable();

        // - Internal
        this.dataService = new DataServiceImpl();

        // - Register listeners
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new TabListListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);

        // - Register outgoing BungeeCord messages.
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // - Disable dangerous commands.
        //TODO: Disable dangerous commands from Core.
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public IScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    @Override
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    @Override
    public TitleManager getTitleManager() {
        return titleManager;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public HeadManager getHeadManager() {
        return headManager;
    }

    @Override
    public WorldShield getWorldShield() {
        return worldShield;
    }

    @Override
    public Announcer getAnnouncer() {
        return announcer;
    }

    @Override
    public RankManager getRankManager() {
        return rankManager;
    }

    @Override
    public ITranslatable getTranslatable() {
        return translatable;
    }

    public DataService getDataService() {
        return dataService;
    }
}
