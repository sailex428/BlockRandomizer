package me.sailex.blockrandomizer;

import me.sailex.blockrandomizer.listener.BlockBreakListener;
import me.sailex.blockrandomizer.listener.InventoryOpenListener;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;
    private MaterialsManager materialsManager;
    private final List<Material> nonSurvivalMaterials = Arrays.asList(
            Material.COMMAND_BLOCK, Material.BARRIER, Material.STRUCTURE_VOID,
            Material.LIGHT, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK, Material.JIGSAW, Material.PETRIFIED_OAK_SLAB, Material.PLAYER_HEAD,
            Material.KNOWLEDGE_BOOK, Material.DEBUG_STICK, Material.BEDROCK,
            Material.REINFORCED_DEEPSLATE, Material.CHORUS_PLANT, Material.BUDDING_AMETHYST,
            Material.END_PORTAL_FRAME, Material.COMMAND_BLOCK_MINECART, Material.INFESTED_COBBLESTONE,
            Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS,
            Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_DEEPSLATE, Material.INFESTED_STONE
    );
    @Override
    public void onEnable() {
        instance = this;
        materialsManager = new MaterialsManager(nonSurvivalMaterials);
        getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);

    }

    public static Main getInstance() {
        return instance;
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

}
