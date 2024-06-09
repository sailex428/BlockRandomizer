package me.sailex.blockrandomizer.command;

import me.sailex.blockrandomizer.BlockRandomizer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomizerGuiCommand implements CommandExecutor {

    private final BlockRandomizer blockRandomizer;

    public RandomizerGuiCommand(BlockRandomizer blockRandomizer) {
        this.blockRandomizer = blockRandomizer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length != 0) {
                return false;
            }

            blockRandomizer.getRandomizerGUI().openGUI((HumanEntity) sender);
            return true;

        }
        return false;
    }
}
