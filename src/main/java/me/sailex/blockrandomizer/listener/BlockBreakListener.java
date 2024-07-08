package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.BlockRandomizer;
import me.sailex.blockrandomizer.materials.MaterialsManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final BlockRandomizer blockRandomizer;
    private final MaterialsManager materialsManager;
    private final Map<String, String> blockToDropMap;
    private final List<Material> materials;
    private final Random random = new Random();

    public BlockBreakListener(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
        materialsManager = blockRandomizer.getMaterialsManager();
        blockToDropMap = materialsManager.getBlockToDropMap();
        materials = materialsManager.getRegisteredMaterials();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!blockRandomizer.getMaterialsManager().getIsBlockRandomizerActive()) {
            return;
        }
        event.setDropItems(false);
        Material blockType = event.getBlock().getType();
        Material drop;

        if (blockToDropMap.containsKey(blockType.name())) {
            drop = Material.valueOf(blockToDropMap.get(blockType.name()));
        } else {
            drop = materials.get(random.nextInt(materials.size()));
            blockToDropMap.put(blockType.name(), drop.name());
            materialsManager.setBlockToDropMap(blockToDropMap);
        }
        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(drop));
    }
}
