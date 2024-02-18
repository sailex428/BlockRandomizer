package me.sailex.blockrandomizer;

import me.sailex.blockrandomizer.command.RandomizerGuiCommand;
import me.sailex.blockrandomizer.gui.RandomizerGUI;
import me.sailex.blockrandomizer.listener.BlockBreakListener;
import me.sailex.blockrandomizer.listener.InventoryClickListener;
import me.sailex.blockrandomizer.listener.InventoryOpenListener;
import me.sailex.blockrandomizer.manager.MaterialsManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;
    private MaterialsManager materialsManager;
    private RandomizerGUI randomizerGUI;

    @Override
    public void onEnable() {
        instance = this;
        List<String> nonSurvivalMaterials = (List<String>) this.getConfig().getList("nonSurvivalMaterials");
        if (nonSurvivalMaterials != null) {
            materialsManager = new MaterialsManager(nonSurvivalMaterials);
            randomizerGUI = new RandomizerGUI();
            getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
            getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
            getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        } else {
            Bukkit.broadcastMessage("§cNo exclusionlist of material found!");
        }
        Objects.requireNonNull(getCommand("randomizer")).setExecutor(new RandomizerGuiCommand());
    }

    @Override
    public void onDisable() {
        materialsManager.setBlockToDropMapConfig();
    }

    public static Main getInstance() {
        return instance;
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

    public RandomizerGUI getRandomizerGUI() {
        return randomizerGUI;
    }

}
