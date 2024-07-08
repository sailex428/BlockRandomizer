package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;

import me.sailex.blockrandomizer.materials.MaterialsManager;
import me.sailex.blockrandomizer.materials.Randomizer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final MaterialsManager materialsManager;
    private final Randomizer randomizer;

    public BlockBreakListener(BlockRandomizer blockRandomizer) {
        this.materialsManager = blockRandomizer.getMaterialsManager();
        this.randomizer = blockRandomizer.getRandomizer();
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
                new ItemStack(randomizer.getRandomizedBlockDrop(event.getBlock().getType()))
        );
    }
}
