package com.n3rdydev.events;

import com.n3rdydev.gui.Kits;
import com.n3rdydev.gui.RecraftRefil;
import com.n3rdydev.gui.SoupRefil;
import com.n3rdydev.settings.config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import com.n3rdydev.entity.player;

public class handleInteract implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

            ItemStack p_tracker = new ItemStack(Material.COMPASS, 1);
            ItemMeta p_tracker_meta = (ItemMeta) p_tracker.getItemMeta();
            p_tracker_meta.setDisplayName("§eRastreador");
            p_tracker.setItemMeta(p_tracker_meta);


            //=========MENUS===========
            if (e.getItem() != null && e.getItem().getType().equals(Material.CHEST)) {
                p.openInventory(Kits.list_kits(p));
                return;
            }

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getType().equals(Material.SIGN_POST) || e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
                    Sign sign = (Sign) e.getClickedBlock().getState();

                    if (sign.getLine(1).contains("Refil de sopa")) {
                        p.openInventory(SoupRefil.open(p));
                    } else if (sign.getLine(1).contains("Refil de recraft")) {
                        p.openInventory(RecraftRefil.open(p));
                    }

                    return;
                }
            }


            //==========KITS==========

            //bussola rastreadora:
            if (e.getItem() != null && e.getItem().equals(p_tracker)) {
                String nickname = "Ninguém";
                Player target = getNearest(p, 250.0);
                float distance = 0;

                //se a vitima for diferente de nula e não está no spawn
                //pegar nome, localização e calcular distancia de blocos
                if (target != null && !is_safe_zone(target.getLocation())) {
                    nickname = target.getPlayer().getName();
                    p.setCompassTarget(target.getLocation());
                    distance = Math.round(p.getLocation().distanceSquared(target.getLocation()));
                }

                p.sendMessage("Jogador mais próximo: " + nickname + "! (" + distance + " metros de distancia).");
                return;
            }

            //kit feeather:
            if(e.getItem() != null && e.getItem().getType().equals(Material.FEATHER) && e.getItem().getItemMeta().getDisplayName().equals("§ePhantom")){
                if(p.getAllowFlight() != true && player.getCooldown(p) != true){
                    p.setAllowFlight(true);
                    p.sendMessage("§aPhantom ativado! desativando em 6 segundos...");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setAllowFlight(false);
                            p.sendMessage("§cPhantom desativado!");
                            player.setCooldown(p, 6);

                        }
                    }.runTaskLater(com.n3rdydev.main.getPlugin(), 100L);
                }
                else{
                    if(player.getCooldown(p) != false){
                        p.sendMessage(player.getCooldownTime(p));
                    }
                    else{
                        p.sendMessage("§cVocê já está com phantom ativado!");
                    }
                }
            }

            //kit kangaroo:
            if (e.getItem() != null && e.getItem().getType().equals(Material.FIREWORK)) {
                if (e.getItem().getItemMeta().getDisplayName().equals("§eKangaroo")) {
                    e.setCancelled(true);
                    if (p.isOnGround()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Vector direction = p.getLocation().getDirection();
                                direction.setY(0.40);
                                direction.multiply(2.0);
                                handleFallDamage.launchpad.put(p.getUniqueId(), true);
                                p.setVelocity(direction);
                            }
                        }.runTaskLater(com.n3rdydev.main.getPlugin(), 1L);

                    }

                    return;
                }

            }

            e.setCancelled(false);
        } else {
            e.setCancelled(false);
        }
    }


    public Player getNearest(Player p, Double range) {
        double distance = Double.POSITIVE_INFINITY;
        Player target = null;
        for (Entity e : p.getNearbyEntities(range, range, range)) {
            if (!(e instanceof Player))
                continue;
            if (e == p) continue;
            double distanceto = p.getLocation().distance(e.getLocation());
            if (distanceto > distance)
                continue;
            distance = distanceto;
            target = (Player) e;
        }
        return target;
    }


    private boolean is_safe_zone(Location location) {

        String spawn_pos1 = config.get().getString("spawn.protection.pos1");
        String spawn_pos2 = config.get().getString("spawn.protection.pos2");
        String[] spawn_sep_1 = spawn_pos1.split(" ");
        String[] spawn_sep_2 = spawn_pos2.split(" ");

        int x1 = Integer.parseInt(spawn_sep_1[0]);
        int x2 = Integer.parseInt(spawn_sep_2[0]);

        int z1 = Integer.parseInt(spawn_sep_1[1]);
        int z2 = Integer.parseInt(spawn_sep_2[1]);

        int minX = Math.min(x1, x2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxZ = Math.max(z1, z2);

        int locX = location.getBlockX();
        int locZ = location.getBlockZ();

        return locX >= minX && locX <= maxX && locZ >= minZ && locZ <= maxZ;
    }
}
