package me.sailex.blockrandomizer.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public class RandomizerInventory implements Listener {

    private final Inventory inv;
    private final ItemStack[] contents = new ItemStack[9];

    public RandomizerInventory() {
        inv = Bukkit.createInventory(null, 9, Component.text("Randomizer"));
        initializeItems();
    }

    public void openInv(final HumanEntity player) {
        inv.setContents(contents);
        player.openInventory(inv);
    }

    private void initializeItems() {
        contents[0] = createInvItem(Material.GRASS_BLOCK, "§aToggle Block Randomizer", "§fBlockdrop will be/not be randomized.");
        contents[1] = createInvItem(Material.CHEST, "§aToggle Chest Randomizer", "§fChestcontent will be/not be randomized.");
        contents[7] = createInvItem(Material.COMMAND_BLOCK, "§dLoad saved Randomizer config", "§fLoads randomizer scheme of the last game played.");
        contents[8] = createInvItem(Material.HEART_OF_THE_SEA, "§cNew world", "§fDeletes current world and creates new world");
    }

    private ItemStack createInvItem(final Material material, final String name, final String lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        meta.lore(Collections.singletonList(Component.text(lore)));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInv() {
        return inv;
    }
}
