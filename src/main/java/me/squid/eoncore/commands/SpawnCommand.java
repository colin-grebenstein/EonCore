package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    EonCore plugin;

    public SpawnCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("spawn")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("eoncommands.spawn.cooldown.immune")) p.teleportAsync(getSpawnLoc());
            else {
                p.sendMessage(Utils.chat(EonCore.prefix + "&7Teleporting in 3 seconds..."));
                Bukkit.getScheduler().runTaskLater(plugin, teleportSpawn(p, getSpawnLoc()), 60L);
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && sender.hasPermission("eoncommands.spawn.others")) {
                target.teleportAsync(getSpawnLoc());
                target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                target.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Spawn-Message")));
            } else sender.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Null")));
        }
        return true;
    }

    private Location getSpawnLoc() {
        double x = -687.5;
        double y = 140.0;
        double z = -708.5;
        return new Location(Bukkit.getWorld("spawn"), x, y, z);
    }

    private Runnable teleportSpawn(Player p, Location spawn) {
        return () -> {
            p.teleportAsync(spawn);
            p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Spawn-Message")));
        };
    }
}
