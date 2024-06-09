package me.sailex.blockrandomizer.materials;

import me.sailex.blockrandomizer.BlockRandomizer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MaterialsManager {

    private final BlockRandomizer blockRandomizer;
    private final List<String> nonSurvivalMaterials;
    private final List<Material> materials = new ArrayList<>();
    private Map<String, String> blockToDropMap = new HashMap<>();
    private boolean isBlockRandomizerActive = true;
    private boolean isChestRandomizerActive = true;
    private static final String configPath = "BLOCK_DROP_MAP";

    public MaterialsManager(List<String> nonSurvivalMaterials, BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        this.nonSurvivalMaterials = nonSurvivalMaterials;
        registerMaterials();
    }

    public void registerMaterials() {
        for (Material material : Material.values()) {
            if (material.isItem() && !nonSurvivalMaterials.contains(material.name())) {
                materials.add(material);
            }
        }
    }

    public boolean loadBlockToDropMap() {
        FileConfiguration config = blockRandomizer.getConfig();
        ConfigurationSection section = config.getConfigurationSection(configPath);
        if (section != null && !section.getKeys(false).isEmpty()) {
            blockToDropMap.clear();
            for (String key : section.getKeys(false)) {
                blockToDropMap.put(key, (String) section.getValues(false).get(key));
                config.get(configPath);
            }
            return true;
        }
        return false;
    }

    public void setBlockToDropMapConfig() {
        blockRandomizer.getConfig().set(configPath, blockToDropMap);
        blockRandomizer.saveConfig();
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public Map<String, String> getBlockToDropMap() {
        return blockToDropMap;
    }

    public void setBlockToDropMap(Map<String, String> blockToDropMap) {
        this.blockToDropMap = blockToDropMap;
    }

    public boolean getIsBlockRandomizerActive() {
        return isBlockRandomizerActive;
    }

    public void setIsBlockRandomizerActive(boolean isRandomizerActive) {
        this.isBlockRandomizerActive = isRandomizerActive;
    }

    public boolean getIsChestRandomizerActive() {
        return isChestRandomizerActive;
    }

    public void setIsChestRandomizerActive(boolean isChestRandomizerActive) {
        this.isChestRandomizerActive = isChestRandomizerActive;
    }
}
