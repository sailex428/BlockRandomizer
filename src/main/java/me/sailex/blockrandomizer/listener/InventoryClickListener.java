package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;
import me.sailex.blockrandomizer.config.ConfigManager;
import me.sailex.blockrandomizer.gui.RandomizerInventory;
import me.sailex.blockrandomizer.materials.MaterialsManager;

import net.kyori.adventure.text.Component;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final ConfigManager configManager;
    private final MaterialsManager materialsManager;
    private final RandomizerInventory randomizerInventory;

    public InventoryClickListener(BlockRandomizer blockRandomizer) {
        this.materialsManager = blockRandomizer.getMaterialsManager();
        this.randomizerInventory = blockRandomizer.getRandomizerInv();
        this.configManager = blockRandomizer.getConfigManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != randomizerInventory.getInv()) {
            return;
        }
        event.setCancelled(true);
        ItemStack currentItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (currentItem == null) {
            return;
        }

        handleBlockRandomizer(currentItem);
        handleChestRandomizer(currentItem);
        handleLoadConfig(currentItem, player);
        handleWorldReset(currentItem);
    }

    private void handleBlockRandomizer(ItemStack currentItem) {
        if (!currentItem.getType().equals(Material.GRASS_BLOCK)) {
            return;
        }
        materialsManager.setIsBlockRandomizerActive(!materialsManager.getIsBlockRandomizerActive());
        randomizerInventory.initializeItems();
    }

    private void handleChestRandomizer(ItemStack currentItem) {
        if (!currentItem.getType().equals(Material.CHEST)) {
            return;
        }
        materialsManager.setIsChestRandomizerActive(!materialsManager.getIsChestRandomizerActive());
        randomizerInventory.initializeItems();
    }

    private void handleLoadConfig(ItemStack currentItem, Player player) {
        if (!currentItem.getType().equals(Material.COMMAND_BLOCK)) {
            return;
        }
        materialsManager.resetBlockToDropMap();
        player.sendMessage("§aA new Randomizer was created!");
    }

    private void handleWorldReset(ItemStack currentItem) {
        if (!currentItem.getType().equals(Material.HEART_OF_THE_SEA)) {
            return;
        }
        Bukkit.getOnlinePlayers().forEach(p -> p.kick(Component.text("§aA new World will be created.")));
        configManager.setDeleteWorldsConfig(true);
        Bukkit.spigot().restart();
    }
}
