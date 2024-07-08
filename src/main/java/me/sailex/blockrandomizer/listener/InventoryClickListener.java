package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;
import me.sailex.blockrandomizer.materials.MaterialsManager;

import net.kyori.adventure.text.Component;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final BlockRandomizer blockRandomizer;

    public InventoryClickListener(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() != this.blockRandomizer.getRandomizerInv().getInv()) {
            return;
        }
        event.setCancelled(true);
        ItemStack currentItem = event.getCurrentItem();
        MaterialsManager manager = this.blockRandomizer.getMaterialsManager();
        Player player = (Player) event.getWhoClicked();

        if (currentItem == null) {
            return;
        }

        if (currentItem.getType().equals(Material.GRASS_BLOCK)) {
            manager.setIsBlockRandomizerActive(!manager.getIsBlockRandomizerActive());
            if (manager.getIsBlockRandomizerActive()) {
                player.sendMessage("§aBlock Randomizer has been activated!");
            } else {
                player.sendMessage("§cBlock Randomizer has been disabled!");
            }
        }

        if (currentItem.getType().equals(Material.CHEST)) {
            manager.setIsChestRandomizerActive(!manager.getIsChestRandomizerActive());
            if (manager.getIsChestRandomizerActive()) {
                player.sendMessage("§aChest Randomizer has been activated!");
            } else {
                player.sendMessage("§cChest Randomizer has been disabled!");
            }
        }

        if (currentItem.getType().equals(Material.COMMAND_BLOCK)) {
            if (manager.loadBlockToDropMap()) {
                player.sendMessage("§aConfiguration has been loaded!");
            } else {
                player.sendMessage("§cNo configuration found!");
            }
        }

        if (currentItem.getType() == Material.HEART_OF_THE_SEA) {
            Bukkit.getOnlinePlayers().forEach(p -> p.kick(Component.text("§aWorld resetting")));
            blockRandomizer.getConfig().set("DELETE_WORLDS_ON_RESTART", true);
            blockRandomizer.saveConfig();
            Bukkit.spigot().restart();
        }
    }
}
