package me.squid.eoncore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BackToDeathLocationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player p;
    private boolean hasCooldown;

    public BackToDeathLocationEvent(Player p, boolean hasCooldown) {
        this.p = p;
        this.hasCooldown = hasCooldown;
    }

    public Player getPlayer() {
        return p;
    }

    public boolean hasCooldown() {
        return hasCooldown;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
