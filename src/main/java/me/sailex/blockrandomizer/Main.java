package me.sailex.blockrandomizer;

import me.sailex.blockrandomizer.listener.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);

    }
}
