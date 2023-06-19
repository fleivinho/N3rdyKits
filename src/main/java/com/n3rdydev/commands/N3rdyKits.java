package com.n3rdydev.commands;

import com.n3rdydev.SQL.MySql;
import com.n3rdydev.scoreboard.sb_default;
import com.n3rdydev.settings.config;
import com.n3rdydev.entity.player;
import com.n3rdydev.settings.serverinfo;
import com.n3rdydev.settings.spawn;
import com.n3rdydev.settings.statistics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class N3rdyKits implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        String mensagem = serverinfo.name() + " | ";

        if (p.hasPermission("n3rdydev.developer.test") || p.hasPermission("n3rdydev.*")) {

            UUID p_uid = p.getUniqueId();
            switch(strings[0]){
                case "reload":
                    config.reload();
                    statistics.reload();
                    spawn.load();
                    mensagem += ("§5Recarregado");
                    break;
                case "setcooldown":
                    player.setCooldown(p, 6);
                    mensagem += ("§aDefinido 6 segundos de cooldown");
                    break;
                case "cooldown":
                    Boolean cooldown = player.getCooldown(p);
                    if(cooldown != false){
                        mensagem += (player.getCooldownTime(p));
                    }
                    break;
                case "addkills":
                    player.addKills(p);
                    sb_default.Set(p);
                    mensagem += ("§5Kill adicionada!");
                    break;
                case "addmortes":
                    player.addDeaths(p);
                    sb_default.Set(p);
                    mensagem += ("§5morte adicionada!");
                    break;
                case "kit":
                    String kit = player.selected_kit.get(p.getUniqueId());
                    kit = kit.toLowerCase();
                    mensagem += ("§5Você está com o kit: "+ kit);
                    break;
                case "save":
                    statistics.get().set(p_uid+".kills", player.getKills(p));
                    statistics.get().set(p_uid+".deaths", player.getDeaths(p));
                    statistics.get().set(p_uid+".xp", 0);
                    statistics.save();
                    break;
                case "teste":
                    MySql.SaveAll();
                    break;

            }
            p.sendMessage(mensagem);

        }
        else{
            p.sendMessage("§cSem permissão");
        }

        return true;
    }
}