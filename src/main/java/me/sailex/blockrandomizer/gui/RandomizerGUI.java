package me.sailex.blockrandomizer.gui;

import io.papermc.paper.text.PaperComponents;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RandomizerGUI implements Listener {

    private final Inventory inv;
    private final ItemStack[] contents = new ItemStack[9];

    public RandomizerGUI() {
        inv = Bukkit.createInventory(null, 9, "Randomizer");
        initializeItems();
    }

    public void openGUI(final HumanEntity player) {
        inv.setContents(contents);
        player.openInventory(inv);
    }

    private void initializeItems() {
        contents[0] = createGuiItem(Material.GREEN_WOOL, "§aToggle Randomizer", "§fblockdrop and chestcontent will be/not be randomized.");
        contents[4] = createGuiItem(Material.COMMAND_BLOCK, "§dLoad saved Randomizer config", "§floads randomizer scheme of the last game played.");
        contents[8] = createGuiItem(Material.RED_WOOL, "§cNew world", "§fdeletes current world and creates new world");
    }

    private ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInv() {
        return inv;
    }
}
