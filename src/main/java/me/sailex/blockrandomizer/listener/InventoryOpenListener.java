package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;
import me.sailex.blockrandomizer.materials.MaterialsManager;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class InventoryOpenListener implements Listener {

    private final BlockRandomizer blockRandomizer;
    private final MaterialsManager materialsManager;
    private final Map<String, String> blockToDropMap;
    private final List<Material> materials;
    private final Random random = new Random();

    public InventoryOpenListener(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        materialsManager = blockRandomizer.getMaterialsManager();
        blockToDropMap = materialsManager.getBlockToDropMap();
        materials = materialsManager.getRegisteredMaterials();
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!blockRandomizer.getMaterialsManager().getIsChestRandomizerActive()) {
            return;
        }
        if (!event.getInventory().getType().equals(InventoryType.CHEST)) {
            return;
        }
        Inventory chestInventory = event.getInventory();
        if (putMetaIfAbsent(chestInventory)) {
            return;
        }
        randomizeChestLoot(chestInventory);
    }

    private void randomizeChestLoot(Inventory chestInventory) {
        ItemStack[] contents = chestInventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            ItemStack drop;
            if (item != null) {
                if (blockToDropMap.containsKey(item.getType().name())) {
                    drop = new ItemStack(Material.valueOf(blockToDropMap.get(item.getType().name())), item.getAmount());
                } else {
                    drop = new ItemStack(materials.get(random.nextInt(materials.size())), item.getAmount());
                    blockToDropMap.put(item.getType().name(), drop.getType().name());
                    materialsManager.setBlockToDropMap(blockToDropMap);
                }
                contents[i] = drop;
            }
        }
        chestInventory.clear();
        chestInventory.setContents(contents);
    }

    private boolean putMetaIfAbsent(Inventory chestInventory) {
        String META_KEY = "randomized";

        if (chestInventory.getHolder() instanceof Chest chest) {
            if (!chest.hasMetadata(META_KEY)) {
                chest.setMetadata(META_KEY, new FixedMetadataValue(blockRandomizer, true));
                return false;
            }
        }

        if (chestInventory.getHolder() instanceof StorageMinecart minecart) {
            if (!minecart.hasMetadata(META_KEY)) {
                minecart.setMetadata(META_KEY, new FixedMetadataValue(blockRandomizer, true));
                return false;
            }
        }

        return true;
    }

}
