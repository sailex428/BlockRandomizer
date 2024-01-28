package me.sailex.blockrandomizer.listener;


import me.sailex.blockrandomizer.Main;
import me.sailex.blockrandomizer.MaterialsManager;
import org.bukkit.Material;
import org.bukkit.block.Chest;
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
    private final MaterialsManager materialsManager = Main.getInstance().getMaterialsManager();
    private final Map<Material, Material> blockToDropMap = materialsManager.getBlockToDropMap();
    private final List<Material> materials = materialsManager.getMaterials();
    private final Random random = new Random();
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        if (event.getInventory().getType() == InventoryType.CHEST) {
            Inventory chestInventory = event.getInventory();
            ItemStack[] contents = chestInventory.getContents();
            Chest chest = (Chest) chestInventory.getHolder();
            if (chest != null) {
                String META_KEY = "randomized";
                if (!chest.hasMetadata(META_KEY)) {
                    for (int i = 0; i < contents.length; i++) {
                        ItemStack item = contents[i];
                        ItemStack drop;
                        if (item != null) {
                            if (blockToDropMap.containsKey(item.getType())) {
                                drop = new ItemStack(blockToDropMap.get(item.getType()), item.getAmount());
                            } else {
                                drop = new ItemStack(materials.get(random.nextInt(materials.size())), item.getAmount());
                                blockToDropMap.put(item.getType(), drop.getType());
                                materialsManager.setBlockToDropMap(blockToDropMap);
                            }
                            contents[i] = drop;
                        }
                    }
                    chestInventory.clear();
                    chestInventory.setContents(contents);
                    chest.setMetadata(META_KEY, new FixedMetadataValue(Main.getInstance(), true));
                }
            }
        }
    }

}
