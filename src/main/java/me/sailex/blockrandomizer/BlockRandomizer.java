package me.sailex.blockrandomizer;

import me.sailex.blockrandomizer.command.RandomizerGuiCommand;
import me.sailex.blockrandomizer.gui.RandomizerGUI;
import me.sailex.blockrandomizer.listener.BlockBreakListener;
import me.sailex.blockrandomizer.listener.InventoryClickListener;
import me.sailex.blockrandomizer.listener.InventoryOpenListener;
import me.sailex.blockrandomizer.materials.MaterialsManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class BlockRandomizer extends JavaPlugin implements Listener {

    private MaterialsManager materialsManager;
    private RandomizerGUI randomizerGUI;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        if (getConfig().getBoolean("DELETE_WORLDS_ON_RESTART")) {
            deleteWorlds();
            getConfig().set("DELETE_WORLDS_ON_RESTART", false);
            saveConfig();
        }
    }

    @Override
    public void onEnable() {
        List<String> excludedMaterials = (List<String>) this.getConfig().getList("EXCLUDED_MATERIALS");
        if (excludedMaterials != null) {
            materialsManager = new MaterialsManager(excludedMaterials, this);
            randomizerGUI = new RandomizerGUI();
            Objects.requireNonNull(getCommand("randomizer")).setExecutor(new RandomizerGuiCommand(this));
            getServer().getPluginManager().registerEvents(new InventoryOpenListener(this), this);
            getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
            getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        } else {
            Bukkit.broadcast(Component.text("§cNo exclusionlist of materials found!"));
        }
    }

    @Override
    public void onDisable() {
        materialsManager.setBlockToDropMapConfig();
    }

    private void deleteWorlds() {
        try {
            String[] worlds = {"world", "world_nether", "world_the_end"};
            for (String world : worlds) {
                File dimension = new File(Bukkit.getWorldContainer(), world);
                Files.walk(dimension.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                if (world.equalsIgnoreCase("world")) {
                    new File(dimension, "playerdata").mkdirs();
                }
            }
        } catch (IOException e) {
            Bukkit.getServer().sendMessage(Component.text("§fError occurred by resetting world!"));
        }

    }

    public RandomizerGUI getRandomizerGUI() {
        return randomizerGUI;
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

}
