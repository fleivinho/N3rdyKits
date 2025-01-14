package com.n3rdydev.events;

import com.n3rdydev.entity.chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class handleChat implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public static void onPlayerChat(PlayerChatEvent e){
        Player p = e.getPlayer();

        if(chat.active_chat != true){
            e.setCancelled(true);
            p.sendMessage("§cChat está desativado!");
            return;
        }
        String cargo = "§7";
        if (p.hasPermission("n3rdydev.cargo.vip")) {
            cargo = "§d[VIP]§f ";
        }

        if (p.hasPermission("n3rdydev.cargo.admin")) {
            cargo = "§c[ADMIN]§f ";
        }
        if (p.hasPermission("n3rdydev.cargo.developer")) {
            cargo = "§6[DEV]§f ";
        }
        e.setFormat(cargo + e.getPlayer().getName() + ": " + e.getMessage());
    }

}
