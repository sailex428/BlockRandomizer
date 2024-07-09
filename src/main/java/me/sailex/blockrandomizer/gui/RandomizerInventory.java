package me.sailex.blockrandomizer.gui;

import me.sailex.blockrandomizer.materials.MaterialsManager;
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
    private final MaterialsManager materialsManager;

    private static final String ON =  "§aON";
    private static final String OFF = "§cOFF";

    public RandomizerInventory(MaterialsManager materialsManager) {
        this.materialsManager = materialsManager;
        inv = Bukkit.createInventory(null, 9, Component.text("Randomizer"));
        initializeItems();
    }

    public void openInv(final HumanEntity player) {
        player.openInventory(inv);
    }

    public void initializeItems() {
        contents[0] = createInvItem(Material.GRASS_BLOCK, "§dBlock Randomizer",
                materialsManager.getIsBlockRandomizerActive() ? ON : OFF);

        contents[1] = createInvItem(Material.CHEST, "§dChest Randomizer",
                materialsManager.getIsChestRandomizerActive() ? ON : OFF);

        contents[7] = createInvItem(Material.COMMAND_BLOCK, "§dCreate new Randomizer",
                "§c- Deletes the current Randomizer");

        contents[8] = createInvItem(Material.HEART_OF_THE_SEA, "§dCreate new World",
                "§c- Deletes the current World");
        inv.setContents(contents);
    }

    private ItemStack createInvItem(final Material material, final String name, final String lore) {
        final ItemStack item = new ItemStack(material, 1);
        item.setItemMeta(createItemMeta(item.getItemMeta(), name, lore));
        return item;
    }

    private ItemMeta createItemMeta(final ItemMeta meta, final String name, final String lore) {
        meta.displayName(Component.text(name));
        meta.lore(Collections.singletonList(Component.text(lore)));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return meta;
    }

    public Inventory getInv() {
        return inv;
    }
}
