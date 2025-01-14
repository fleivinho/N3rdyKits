package com.n3rdydev.events;

import com.n3rdydev.entity.player;
import com.n3rdydev.gui.Config;
import com.n3rdydev.settings.config;
import com.n3rdydev.settings.serverinfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.WOOL;

public class handleConfig implements Listener {

    //essa classe irá controlar oq o usuário vai definir
    //no comando /definir (exemplo: se ele clicar na cama, ele irá pegar a posição X,Y,Z do jogador)
    //e irá definir nas configurações o spawn...

    //Status: gui não criado...
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        //Pagina inicial
        if (e.getView().getTitle().equals("Configuração") && e.getCurrentItem() != null) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case BED:
                    p.openInventory(Config.spawn(p));
                    break;
                case COMPASS:
                    p.openInventory(Config.warps(p));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case AIR:
                default:
                    p.openInventory(Config.start(p));
                    break;
            }
        }
        //pagina spawn
        if (e.getView().getTitle().equals("Spawn Config") && e.getCurrentItem() != null) {
            e.setCancelled(true);
            p.closeInventory();
            switch (e.getCurrentItem().getType()) {
                case NETHER_STAR:
                    String spawn_format = (p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                    config.get().set("spawn.point", spawn_format);
                    config.save();
                    config.reload();
                    com.n3rdydev.settings.spawn.load();
                    p.sendMessage(serverinfo.name() + " §aVocê definiu o novo spawn! (" + spawn_format + ")");
                    break;
                case DIAMOND_SWORD:
                    //config_menu
                    player.config_menu.put(p.getUniqueId(), true);
                    p.getInventory().clear();
                    ItemStack protect_item = new ItemStack(Material.BEACON);
                    ItemMeta protect_item_meta = protect_item.getItemMeta();
                    protect_item_meta.setDisplayName("§eProteção do Spawn - POSIÇÃO 1");
                    protect_item.setItemMeta(protect_item_meta);
                    p.getInventory().setItem(0, protect_item);
                    p.sendMessage(serverinfo.name()+ " | §ePosicione um ponto x e um ponto contrário do y do spawn para definir a proteção.");
                    p.closeInventory();
                    break;
                case SIGN:
                    p.closeInventory();
                    p.openInventory(Config.start(p));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case AIR:
                default:
                    p.openInventory(Config.spawn(p));
                    break;
            }


        }
        //pagina warps
        if (e.getView().getTitle().equals("Warps Config") && e.getCurrentItem() != null) {
            e.setCancelled(true);
            p.closeInventory();
            switch (e.getCurrentItem().getType()) {
                case GLASS:
                    p.openInventory(Config.warp_fps(p));
                    break;
                case SIGN:
                    p.closeInventory();
                    p.openInventory(Config.start(p));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case AIR:
                default:
                    p.openInventory(Config.spawn(p));
                    break;
            }
        }
        //CABOU

        //pagina warp fps
        if (e.getView().getTitle().equals("FPS CONFIG") && e.getCurrentItem() != null) {
            e.setCancelled(true);
            p.closeInventory();


            switch (e.getCurrentItem().getType()) {

                case WOOL:
                    if(com.n3rdydev.settings.config.get().getBoolean("warps.fps.active") != false){
                        //se tiver true
                        config.get().set("warps.fps.active", false);
                        config.save();
                        p.sendMessage(serverinfo.name() + " | §cWarp fps foi desativada!");
                    }
                    else{
                        config.get().set("warps.fps.active", true);
                        config.save();
                        p.sendMessage(serverinfo.name() + " | §aWarp fps foi ativada!");
                    }
                    p.closeInventory();
                    p.openInventory(Config.warp_fps(p));
                    break;

                case BED:
                    String format = p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ();
                    config.get().set("warps.fps.active", true);
                    config.get().set("warps.fps.spawnpos", format);
                    config.save();
                    p.sendMessage(serverinfo.name() + " | §aWarp fps foi definida na posição atual!");
                    break;
                case SIGN:
                    p.closeInventory();
                    p.openInventory(Config.warps(p));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case AIR:
                default:
                    p.openInventory(Config.warp_fps(p));
                    break;
            }
        }
        //CABOU





    }

}
