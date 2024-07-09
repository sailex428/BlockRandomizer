package me.sailex.blockrandomizer.materials;

import me.sailex.blockrandomizer.BlockRandomizer;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Randomizer {

    private static final String META_KEY = "randomized";

    private final Map<String, String> blockToDropMap;
    private final List<Material> registeredMaterials;
    private final BlockRandomizer blockRandomizer;

    private final Random random = new Random();

    public Randomizer(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        this.blockToDropMap = blockRandomizer.getMaterialsManager().getBlockToDropMap();
        this.registeredMaterials = blockRandomizer.getMaterialsManager().getRegisteredMaterials();
    }

    public Material getRandomizedBlockDrop(Material blockType) {
        Material drop;
        if (blockToDropMap.containsKey(blockType.name())) {
            drop = Material.valueOf(blockToDropMap.get(blockType.name()));
        } else {
            drop = registeredMaterials.get(random.nextInt(registeredMaterials.size()));
            blockToDropMap.put(blockType.name(), drop.name());
        }
        return drop;
    }

    private ItemStack getRandomizedItem(ItemStack item) {
        Material material = item.getType();
        ItemStack drop;
        if (blockToDropMap.containsKey(material.name())) {
            drop = new ItemStack(Material.valueOf(blockToDropMap.get(material.name())), item.getAmount());
        } else {
            drop = new ItemStack(registeredMaterials.get(random.nextInt(registeredMaterials.size())), item.getAmount());
            blockToDropMap.put(material.name(), drop.getType().name());
        }
        return drop;
    }

    public void randomizeChestLoot(Inventory chestInventory) {
        ItemStack[] contents = chestInventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) {
                continue;
            }
            contents[i] = this.getRandomizedItem(item);
        }
        chestInventory.clear();
        chestInventory.setContents(contents);
    }

    public boolean isRandomizedInventory(InventoryHolder inventoryHolder) {
        return isRandomizedChestInventory(inventoryHolder) || isRandomizedMinecartInventory(inventoryHolder);
    }

    private boolean isRandomizedChestInventory(InventoryHolder inventoryHolder) {
        if (inventoryHolder instanceof Chest chest) {
            if (chest.hasMetadata(META_KEY)) {
                return true;
            }
            chest.setMetadata(META_KEY, new FixedMetadataValue(blockRandomizer, true));
        }
        return false;
    }

    private boolean isRandomizedMinecartInventory(InventoryHolder inventoryHolder) {
        if (inventoryHolder instanceof StorageMinecart minecart) {
            if (minecart.hasMetadata(META_KEY)) {
                return true;
            }
            minecart.setMetadata(META_KEY, new FixedMetadataValue(blockRandomizer, true));
        }
        return false;
    }

}
