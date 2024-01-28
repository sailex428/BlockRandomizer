package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.Main;
import me.sailex.blockrandomizer.MaterialsManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final MaterialsManager materialsManager = Main.getInstance().getMaterialsManager();
    private final Map<Material, Material> blockToDropMap = materialsManager.getBlockToDropMap();
    private final List<Material> materials = materialsManager.getMaterials();
    private final Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        event.setDropItems(false);
        Material blockType = event.getBlock().getType();
        Material drop;

        if (blockToDropMap.containsKey(blockType)) {
            drop = blockToDropMap.get(blockType);
        } else {
            drop = materials.get(random.nextInt(materials.size()));
            blockToDropMap.put(blockType, drop);
            materialsManager.setBlockToDropMap(blockToDropMap);
        }
        Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItemNaturally(event.getBlock().getLocation(), new ItemStack(drop));

    }

}
