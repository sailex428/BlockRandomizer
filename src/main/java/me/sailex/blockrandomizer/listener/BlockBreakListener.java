package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.materials.MaterialsManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final MaterialsManager materialsManager;

    public BlockBreakListener(MaterialsManager materialsManager) {
        this.materialsManager = materialsManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!materialsManager.getIsBlockRandomizerActive()) {
            return;
        }
        event.setDropItems(false);

        Location currentBlockLocation = event.getBlock().getLocation();
        currentBlockLocation.getWorld().dropItemNaturally(
                currentBlockLocation,
                new ItemStack(materialsManager.getRandomizedBlockDrop(event.getBlock().getType()))
        );
    }
}
