package com.n3rdydev.commands;

import com.n3rdydev.settings.serverinfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameMode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if (!(p.hasPermission("n3rdydev.command.gm") || p.hasPermission("n3rdydev.*"))) {
            p.sendMessage("§cSem permissão!");
            return true;
        }

        switch (strings[0]) {
            case "1":
            case "C":
            case "c":
                p.setGameMode(org.bukkit.GameMode.CREATIVE);
                p.sendMessage(serverinfo.name() + " §aModo de jogo alterado para CRIATIVO");
                return true;

            case "s":
            case "0":
            case "S":
                p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                p.sendMessage(serverinfo.name() + " §aModo de jogo alterado para SURVIVAL");
                return true;
            default:
                return false;
        }


    }
}
