package com.n3rdydev.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.*;

public class Kits {
    public static Inventory list_kits(Player p) {
        //                                  Player | tamanho 3 linhas com 9 colunas | Nome que aparece em cima
        Inventory inv = Bukkit.createInventory(p, 9 * 3, "Lista de Kits");

        ItemStack nao_possui = new ItemStack(STAINED_GLASS_PANE, 1, (byte)14);

        for (int v = 0; v <= 26; v++) {
            switch (v) {
                case 0:
                    inv.setItem(v, createItem(new ItemStack(DIAMOND_SWORD), "§aPvP", "§7Kit padrão", " ", "§eClique para selecionar."));
                    break;
                case 1:
                    if(p.hasPermission("n3rdydev.kit.kangaroo")){
                        inv.setItem(v, createItem(new ItemStack(FIREWORK), "§aKangaroo", "§7Foguetinho", " ", "§eClique para selecionar."));
                    }
                    else{
                        inv.setItem(v, createItem(nao_possui, "§cKangaroo", "§7Foguetinho", "\n §cNão possui", "§eClique para selecionar."));
                    }
                    break;
                case 2:
                    if(p.hasPermission("n3rdydev.kit.boxer")){
                        inv.setItem(v, createItem(new ItemStack(STONE_SWORD), "§aBoxer", "§7Kit Boxer", " ", "§eClique para selecionar."));
                    }
                    else{
                        inv.setItem(v, createItem(nao_possui, "§aBoxer", "§7Kit Boxer", "\n §cNão possui\n", "§eClique para selecionar."));
                    }

                    break;
                case 3:
                    if(p.hasPermission("n3rdydev.kit.archer")){
                        inv.setItem(v, createItem(new ItemStack(BOW), "§aArcher", "§7Kit Archer", "", "§eClique para selecionar."));
                    }
                    else{
                        inv.setItem(v, createItem(nao_possui, "§aArcher", "§7Kit Archer", "\n §cNão possui\n", "§eClique para selecionar."));
                    }
                    break;
                case 4:
                    inv.setItem(v, createItem(new ItemStack(IRON_BOOTS), "§aStomper", "§7Kit Stomper", " ", "§eClique para selecionar."));
                    break;
                case 5:
                    inv.setItem(v, createItem(new ItemStack(NETHER_STAR), "§aNinja", "Kit Ninja", " ", "§eClique para selecionar."));
                    break;
                case 6:
                    inv.setItem(v, createItem(new ItemStack(FEATHER), "§aPhantom", "Kit Phantom", " ", "§eClique para selecionar."));
                    break;
                default:
                    inv.setItem(v, createItem(new ItemStack(THIN_GLASS), "§aVazio", " ", " ", " "));
                    break;
            }
        }


        return inv;
    }

    private static ItemStack createItem(ItemStack item, String nome, String... descricao) {

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));
        List<String> lores = new ArrayList<>();
        for (String s : descricao) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }


}
