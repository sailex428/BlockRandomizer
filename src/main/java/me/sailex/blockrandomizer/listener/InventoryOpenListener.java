package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;

import me.sailex.blockrandomizer.materials.Randomizer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryOpenListener implements Listener {

    private final BlockRandomizer blockRandomizer;
    private final Randomizer randomizer;

    public InventoryOpenListener(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        randomizer = blockRandomizer.getRandomizer();
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!blockRandomizer.getMaterialsManager().getIsChestRandomizerActive()) {
            return;
        }
        Inventory chestInventory = event.getInventory();
        if (!chestInventory.getType().equals(InventoryType.CHEST)) {
            return;
        }
        if (chestInventory.equals(blockRandomizer.getRandomizerInv().getInv())) {
            return;
        }
        if (randomizer.isRandomizedInventory(chestInventory.getHolder())) {
            return;
        }
        randomizer.randomizeChestLoot(chestInventory);
    }

}
