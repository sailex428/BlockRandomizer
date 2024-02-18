package me.sailex.blockrandomizer.manager;

import me.sailex.blockrandomizer.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MaterialsManager {

    private final List<String> nonSurvivalMaterials;
    private final List<Material> materials = new ArrayList<>();
    private Map<String, String> blockToDropMap = new HashMap<>();
    private boolean isRandomizerActive = false;
    private static final String configPath = "blockToDropMap_BACKUP";

    public MaterialsManager(List<String> nonSurvivalMaterials) {
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

    public List<Material> getMaterials() {
        return materials;
    }

    public Map<String, String> getBlockToDropMap() {
        return blockToDropMap;
    }

    public void setBlockToDropMap(Map<String, String> blockToDropMap) {
        this.blockToDropMap = blockToDropMap;
    }

    public boolean loadBlockToDropMap() {
        FileConfiguration config = Main.getInstance().getConfig();
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
        Main main = Main.getInstance();
        main.getConfig().set(configPath, blockToDropMap);
        main.saveConfig();
    }

    public boolean getIsRandomizerActive() {
        return isRandomizerActive;
    }
    public void setIsRandomizerActive(boolean isRandomizerActive) {
        this.isRandomizerActive = isRandomizerActive;
    }
}
