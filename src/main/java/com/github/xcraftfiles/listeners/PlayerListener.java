package com.github.xcraftfiles.listeners;

import com.github.xcraftfiles.XCraftFiles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final XCraftFiles plugin;
    
    public PlayerListener(XCraftFiles plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getTelegramManager().sendToTelegram("👤 Агент " + player.getName() + " присоединился к серверу");
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getTelegramManager().sendToTelegram("👤 Агент " + player.getName() + " покинул сервер");
    }
}
