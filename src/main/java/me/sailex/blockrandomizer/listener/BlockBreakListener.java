package me.sailex.blockrandomizer.listener;

import me.sailex.blockrandomizer.Main;
import me.sailex.blockrandomizer.manager.MaterialsManager;
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
    private final Map<String, String> blockToDropMap = materialsManager.getBlockToDropMap();
    private final List<Material> materials = materialsManager.getMaterials();
    private final Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (Main.getInstance().getMaterialsManager().getIsRandomizerActive()) {

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
            Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItemNaturally(event.getBlock().getLocation(), new ItemStack(drop));

        }

    }

}
