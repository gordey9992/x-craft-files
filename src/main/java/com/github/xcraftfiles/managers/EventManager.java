package com.github.xcraftfiles.managers;

import com.github.xcraftfiles.XCraftFiles;
import com.github.xcraftfiles.utils.CrypticMessages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class EventManager {
    private final XCraftFiles plugin;
    private final Random random;
    private final CrypticMessages crypticMessages;

    public EventManager(XCraftFiles plugin) {
        this.plugin = plugin;
        this.random = new Random();
        this.crypticMessages = new CrypticMessages();
    }

    public void startEventScheduler() {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkForRandomEvents();
            }
        }.runTaskTimer(plugin, 1200L, 2400L); // Проверка каждые 1-2 минуты
    }

    private void checkForRandomEvents() {
        World world = Bukkit.getWorlds().get(0);
        if (world.getPlayerCount() == 0) return;

        int chance = random.nextInt(100);
        if (chance < plugin.getConfig().getInt("events.base-event-chance", 15)) {
            triggerRandomEvent(world);
        }
    }

    private void triggerRandomEvent(World world) {
        int eventType = random.nextInt(10);
        Player randomPlayer = getRandomPlayer();
        Location location = randomPlayer != null ? randomPlayer.getLocation() : 
                           new Location(world, random.nextInt(2000) - 1000, 64, random.nextInt(2000) - 1000);

        switch (eventType) {
            case 0: case 1:
                triggerUFOSighting(location);
                break;
            case 2: case 3:
                triggerGhostActivity(location);
                break;
            case 4:
                triggerAlienAbduction(location);
                break;
            case 5:
                triggerMutantSighting(location);
                break;
            case 6:
                triggerShadowCreature(location);
                break;
            case 7:
                triggerStrangeSounds(location);
                break;
            case 8:
                triggerEMFReading(location);
                break;
            case 9:
                triggerCrypticMessage();
                break;
        }
    }

    private void triggerUFOSighting(Location location) {
        World world = location.getWorld();
        world.strikeLightningEffect(location);
        world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 0.5f);
        
        String message = "👽 НЛО замечено в секторе " + location.getBlockX() + ", " + location.getBlockZ() + "! ТРЕВОГА!";
        Bukkit.broadcastMessage("§3👽 §b" + message);
        
        plugin.getTelegramManager().broadcastEvent("НЛО Наблюдение", 
            location.getBlockX() + ", " + location.getBlockZ(), null);
    }

    private void triggerGhostActivity(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_GHOST_AMBIENT, 1.0f, 1.0f);
        world.spawnParticle(Particle.SOUL, location, 50, 3, 3, 3);
        
        String message = "👻 Паранормальная активность в " + location.getBlockX() + ", " + location.getBlockZ() + ". ЭМП зашкаливает!";
        Bukkit.broadcastMessage("§3👻 §b" + message);
        
        plugin.getTelegramManager().broadcastEvent("Паранормальная Активность", 
            location.getBlockX() + ", " + location.getBlockZ(), null);
    }

    private void triggerAlienAbduction(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.7f);
        world.spawnParticle(Particle.PORTAL, location, 100, 2, 2, 2);
        
        Bukkit.broadcastMessage("§3👽 §bЗафиксировано похищение! Координаты: " + location.getBlockX() + ", " + location.getBlockZ());
    }

    private void triggerMutantSighting(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_ZOMBIE_AMBIENT, 1.0f, 0.8f);
        
        Bukkit.broadcastMessage("§3🧬 §bМутант замечен в " + location.getBlockX() + ", " + location.getBlockZ() + "! Осторожно!");
    }

    private void triggerShadowCreature(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_WARDEN_AMBIENT, 0.5f, 1.2f);
        world.spawnParticle(Particle.SQUID_INK, location, 30, 2, 2, 2);
        
        Bukkit.broadcastMessage("§3🌑 §bТеневое существо в " + location.getBlockX() + ", " + location.getBlockZ() + "! Не приближаться!");
    }

    private void triggerStrangeSounds(Location location) {
        location.getWorld().playSound(location, Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 0.7f, 1.0f);
        Bukkit.broadcastMessage("§3🔊 §bСлышны странные звуки... Шепот из ниоткуда...");
    }

    private void triggerEMFReading(Location location) {
        Player nearest = getNearestPlayer(location);
        if (nearest != null) {
            nearest.playSound(nearest.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 2.0f);
            nearest.sendMessage("§3📡 §bЭМП сигнал: §e" + (random.nextInt(5) + 1) + "§b. Источник рядом...");
        }
    }

    private void triggerCrypticMessage() {
        String message = crypticMessages.getRandomMessage();
        Bukkit.broadcastMessage(message);
    }

    private Player getRandomPlayer() {
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        return players.length > 0 ? players[random.nextInt(players.length)] : null;
    }

    private Player getNearestPlayer(Location location) {
        Player nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            double distance = player.getLocation().distance(location);
            if (distance < nearestDistance && distance < 50) {
                nearest = player;
                nearestDistance = distance;
            }
        }
        return nearest;
    }
}
