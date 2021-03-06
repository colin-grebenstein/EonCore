package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.MobArenaGUI;
import me.squid.eoncore.tasks.WarpTeleportTask;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WarpsListener implements Listener {

    EonCore plugin;
    Location utilities, crates, market, endPortal, chilis, mine;
    MobArenaGUI mobArenaGUI = new MobArenaGUI();

    public WarpsListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        initializeWarps();
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onWarp(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(Utils.chat("&5&lEon Warps"))) {
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            switch (e.getCurrentItem().getType()) {
                case ANVIL:
                    p.closeInventory();
                    teleportPlayerToWarp(p, utilities, name);
                    break;
                case CHEST:
                    p.closeInventory();
                    teleportPlayerToWarp(p, crates, name);
                    break;
                case EMERALD:
                    p.closeInventory();
                    teleportPlayerToWarp(p, market, name);
                    break;
                case END_PORTAL_FRAME:
                    p.closeInventory();
                    teleportPlayerToWarp(p, endPortal, name);
                    break;
                case ZOMBIE_HEAD:
                    p.closeInventory();
                    p.openInventory(mobArenaGUI.MainGUI());
                    break;
                case BREAD:
                    p.closeInventory();
                    //teleportPlayerToWarp(p, chilis, name);
                    p.sendMessage(Utils.chat(EonCore.prefix + "&7Chilis has disappeared. It will be back soon"));
                    break;
                case DIAMOND_ORE:
                    p.closeInventory();
                    if (p.hasPermission("eonwarps.basicmine")) teleportPlayerToWarp(p, mine, name);
                    else p.sendMessage(Utils.chat(EonCore.prefix + "&7You do not have access to this warp. Rankup to Ranger (/ranks)."));
                    break;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            e.setCancelled(true);
        }
    }

    public void teleportPlayerToWarp(Player player, Location location, String warpName) {
        if (player.hasPermission("eoncommands.warp.cooldown.bypass")) {
            new WarpTeleportTask(plugin, location, player, warpName).runTaskAsynchronously(plugin);
        } else {
            player.sendMessage(Utils.chat(EonCore.prefix + "&7Teleporting in 3 seconds..."));
            new WarpTeleportTask(plugin, location, player, warpName).runTaskLaterAsynchronously(plugin, plugin.getConfig().getLong("Warp-Delay") * 20);
        }
    }

    public void initializeWarps() {
        utilities = new Location(Bukkit.getWorld("spawn"), -595.5, 81, -225.5);
        crates = new Location(Bukkit.getWorld("spawn"), -687.5 ,174, -714.5);
        market = new Location(Bukkit.getWorld("spawn"), -323, 90, -590, 180, 0);
        endPortal = new Location(Bukkit.getWorld("world"), 21830, 23, 10306, 90, 0);
        chilis = new Location(Bukkit.getWorld("spawn"), -323, 90, -590, 180, 0);
        mine = new Location(Bukkit.getWorld("spawn"), -430, 95, -294, 90, 0);
    }
}
