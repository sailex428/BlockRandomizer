package me.sailex.blockrandomizer;

import me.sailex.blockrandomizer.command.RandomizerInvCommand;
import me.sailex.blockrandomizer.config.ConfigManager;
import me.sailex.blockrandomizer.gui.RandomizerInventory;
import me.sailex.blockrandomizer.listener.BlockBreakListener;
import me.sailex.blockrandomizer.listener.InventoryClickListener;
import me.sailex.blockrandomizer.listener.InventoryOpenListener;
import me.sailex.blockrandomizer.materials.MaterialsManager;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BlockRandomizer extends JavaPlugin implements Listener {

    private MaterialsManager materialsManager;
    private RandomizerInventory randomizerInventory;
    private ConfigManager configManager;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        configManager = new ConfigManager(this);
        configManager.resetWorldsOnRestart();
    }

    @Override
    public void onEnable() {
        materialsManager = new MaterialsManager(configManager);
        randomizerInventory = new RandomizerInventory(materialsManager);

        registerCommands();
        registerListener();
    }

    @Override
    public void onDisable() {
        configManager.setBlockToDropMapConfig(materialsManager.getBlockToDropMap());
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new InventoryOpenListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(materialsManager), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this, configManager), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("randomizer")).setExecutor(new RandomizerInvCommand(this));
    }

    public RandomizerInventory getRandomizerInv() {
        return randomizerInventory;
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

}
