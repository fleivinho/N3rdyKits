package com.n3rdydev.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class kits extends JavaPlugin implements Listener {
    public static Inventory list_kits(Player p){
        //                                  Player | tamanho 3 linhas com 9 colunas | Nome que aparece em cima
        Inventory inv = Bukkit.createInventory(p, 9*3, "Lista de Kits");

        inv.setItem(0, createItem(new ItemStack(Material.DIAMOND_SWORD), "PvP", "Kit padrão", "§6Clique para selecionar!"));
        return inv;
    }
    private static ItemStack createItem(ItemStack item, String nome, String ... descricao){

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        List<String> lores = new ArrayList<>();
        for (String s : descricao){
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }

}