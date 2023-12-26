package me.sailex.blockrandomizer.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockBreakListener implements Listener {

    private final List<Material> materials = new ArrayList<>();
    private final Random random = new Random();
    private final Map<Material, Material> blockToDropMapping = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        event.setDropItems(false);

        for (Material material : Material.values()) {
            if (material.isItem()) {
                materials.add(material);
            }
        }

        Material blockType = event.getBlock().getType();
        Material drop;

        if (blockToDropMapping.containsKey(blockType)) {
            drop = blockToDropMapping.get(blockType);
        } else {
            drop = materials.get(random.nextInt(materials.size()));
            blockToDropMapping.put(blockType, drop);
        }

        Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItemNaturally(event.getBlock().getLocation(), new ItemStack(drop));

    }

}
