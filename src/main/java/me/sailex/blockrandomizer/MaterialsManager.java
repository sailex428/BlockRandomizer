package me.sailex.blockrandomizer;

import org.bukkit.Material;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialsManager {

    private final List<Material> nonSurvivalMaterials;
    private final List<Material> materials = new ArrayList<>();
    private Map<Material, Material> blockToDropMap = new HashMap<>();

    public MaterialsManager(List<Material> nonSurvivalMaterials) {
        this.nonSurvivalMaterials = nonSurvivalMaterials;
        registerMaterials();
    }

    public void registerMaterials() {
        for (Material material : Material.values()) {
            if (material.isItem() && !nonSurvivalMaterials.contains(material)) {
                materials.add(material);
            }
        }
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public Map<Material, Material> getBlockToDropMap() {
        return blockToDropMap;
    }

    public void setBlockToDropMap(Map<Material, Material> blockToDropMap) {
        this.blockToDropMap = blockToDropMap;
    }

}
