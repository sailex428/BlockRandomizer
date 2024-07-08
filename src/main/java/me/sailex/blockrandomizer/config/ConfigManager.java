package me.sailex.blockrandomizer.config;

import me.sailex.blockrandomizer.BlockRandomizer;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static me.sailex.blockrandomizer.config.ConfigPaths.*;

public class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    private final BlockRandomizer blockRandomizer;
    private final FileConfiguration config;
    private List<String> excludedMaterials;

    public ConfigManager(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        this.config = blockRandomizer.getConfig();
    }

    public void resetWorldsOnRestart() {
        if (!config.getBoolean(DELETE_WORLDS_ON_RESTART)) {
            return;
        }
        deleteAllWorlds();
        config.set(DELETE_WORLDS_ON_RESTART, false);
        blockRandomizer.saveConfig();
    }

    public void setDeleteWorldsConfig(boolean deleteWorldsOnRestart) {
        config.set(DELETE_WORLDS_ON_RESTART, deleteWorldsOnRestart);
        blockRandomizer.saveConfig();
    }

    private void deleteAllWorlds() {
        String[] worldNames = {"world", "world_nether", "world_the_end"};
        for (String worldName : worldNames) {
            deleteSingleWorld(worldName);
        }
    }

    private void deleteSingleWorld(String worldName) {
        File dimension = new File(Bukkit.getWorldContainer(), worldName);
        try (Stream<Path> paths = Files.walk(dimension.toPath())) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (!file.delete()) {
                            LOGGER.warn("Failed to delete: {}", file.getName());
                        }
                    });
        } catch (IOException error) {
            LOGGER.error("Error occurred by resetting world!", error);
        }
    }

    public boolean loadConfigMap(Map<String, String> stringMap, String mapConfigPath) {
        ConfigurationSection section = config.getConfigurationSection(mapConfigPath);
        if (section == null) {
            LOGGER.error("Failed to load {}", mapConfigPath);
            return false;
        }
        stringMap.clear();
        for (String key : section.getKeys(false)) {
            stringMap.put(key, (String) section.getValues(false).get(key));
        }
        return true;
    }

    public void setBlockToDropMapConfig(Map<String, String> blockToDropMap) {
        config.set(BLOCK_DROP_MAP, blockToDropMap);
        blockRandomizer.saveConfig();
    }

    public List<String> loadExcludedMaterials() {
        List<String> excludedMaterials = config.getStringList(EXCLUDED_MATERIALS);
        if (excludedMaterials != null) {
            return excludedMaterials;
        }
        LOGGER.warn("Excluded materials not set!");
        return new ArrayList<>();
    }

}
