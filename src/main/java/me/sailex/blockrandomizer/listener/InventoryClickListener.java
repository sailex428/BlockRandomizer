package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.Main;
import me.sailex.blockrandomizer.manager.MaterialsManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == Main.getInstance().getRandomizerGUI().getInv()) {

            if (event.getWhoClicked() instanceof Player) {

                event.setCancelled(true);
                ItemStack currentItem = event.getCurrentItem();
                MaterialsManager manager = Main.getInstance().getMaterialsManager();
                Player player = (Player) event.getWhoClicked();

                if (currentItem != null) {

                    if (currentItem.getType() == Material.GREEN_WOOL) {
                        manager.setIsRandomizerActive(!manager.getIsRandomizerActive());
                        if (manager.getIsRandomizerActive()) {
                            player.sendMessage("§aRandomizer has been activated!");
                        } else {
                            player.sendMessage("§cRandomizer has been disabled!");
                        }
                    }

                    if (currentItem.getType() == Material.COMMAND_BLOCK) {
                        if (manager.loadBlockToDropMap()) {
                            player.sendMessage("§aRandomizer configuration of last played challenge has been loaded!");
                        } else {
                            player.sendMessage("§cNo configuration found!");
                        }
                    }

                    if (currentItem.getType() == Material.RED_WOOL) {
                        Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer( "§aWorld reset"));
                        try {
                            String[] worlds = {"world", "world_nether", "world_the_end"};
                            for (String world : worlds) {
                                File dimension = new File(Bukkit.getWorldContainer(), world);
                                Files.walk(dimension.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                            }

                        } catch (IOException e) {
                            System.out.println("§ferror by resetting world!");
                        }
                        Bukkit.spigot().restart();
                    }
                }
            }
        }
    }
}
