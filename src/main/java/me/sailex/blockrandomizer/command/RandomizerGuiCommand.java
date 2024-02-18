package me.sailex.blockrandomizer.command;

import me.sailex.blockrandomizer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class RandomizerGuiCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length == 0) {
                Main.getInstance().getRandomizerGUI().openGUI((HumanEntity) sender);
            }

        }
        return false;
    }
}
