package me.sailex.blockrandomizer.materials;

import me.sailex.blockrandomizer.config.ConfigManager;
import me.sailex.blockrandomizer.config.ConfigPaths;
import org.bukkit.Material;

import java.util.*;

public class MaterialsManager {

    private final List<String> excludedMaterials;
    private final List<Material> registeredMaterials = new ArrayList<>();
    private final Map<String, String> blockToDropMap = new HashMap<>();

    private boolean isBlockRandomizerActive = true;
    private boolean isChestRandomizerActive = true;

    public MaterialsManager(ConfigManager configManager) {
        this.excludedMaterials = configManager.loadExcludedMaterials();
        configManager.loadConfigMap(blockToDropMap, ConfigPaths.BLOCK_DROP_MAP);
        registerMaterials();
    }

    public void registerMaterials() {
        for (Material material : Material.values()) {
            if (material.isItem() && !excludedMaterials.contains(material.name())) {
                registeredMaterials.add(material);
            }
        }
    }

    public void resetBlockToDropMap() {
        blockToDropMap.clear();
    }

    public List<Material> getRegisteredMaterials() {
        return registeredMaterials;
    }

    public Map<String, String> getBlockToDropMap() {
        return blockToDropMap;
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
