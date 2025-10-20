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
        }.runTaskTimer(plugin, 1200L, 2400L);

        new BukkitRunnable() {
            @Override
            public void run() {
                checkForRareScaryEvents();
            }
        }.runTaskTimer(plugin, 6000L, 12000L);
    }

    private void checkForRandomEvents() {
        World world = Bukkit.getWorlds().get(0);
        if (world.getPlayerCount() == 0) return;

        int chance = random.nextInt(100);
        if (chance < plugin.getConfig().getInt("events.base-event-chance", 15)) {
            triggerRandomEvent(world);
        }
    }

    private void checkForRareScaryEvents() {
        World world = Bukkit.getWorlds().get(0);
        if (world.getPlayerCount() == 0) return;

        int chance = random.nextInt(100);
        if (chance < 5) {
            triggerScaryEvent(world);
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

    private void triggerScaryEvent(World world) {
        int scaryType = random.nextInt(6);
        Player randomPlayer = getRandomPlayer();
        if (randomPlayer == null) return;

        Location location = randomPlayer.getLocation();

        switch (scaryType) {
            case 0:
                triggerWhispersInMind(randomPlayer);
                break;
            case 1:
                triggerShadowFigure(location);
                break;
            case 2:
                triggerTimeAnomaly(randomPlayer);
                break;
            case 3:
                triggerEntityStare(randomPlayer);
                break;
            case 4:
                triggerGravityAnomaly(location);
                break;
            case 5:
                triggerMemoryLoss(randomPlayer);
                break;
        }
    }

    private void triggerUFOSighting(Location location) {
        World world = location.getWorld();
        world.strikeLightningEffect(location);
        world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 0.5f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        String message = "👽 НЛО замечено в секторе " + coords + "! ТРЕВОГА!";
        Bukkit.broadcastMessage("§3👽 §b" + message);
        
        plugin.getTelegramManager().broadcastEvent("НЛО Наблюдение", coords, "Неизвестно");
    }

    private void triggerGhostActivity(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_PHANTOM_AMBIENT, 1.0f, 0.8f);
        world.playSound(location, Sound.ENTITY_VEX_AMBIENT, 0.8f, 0.5f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3👻 §bПаранормальная активность в " + coords + ". ЭМП зашкаливает!");
        
        plugin.getTelegramManager().broadcastEvent("Паранормальная Активность", coords, "Неизвестно");
    }

    private void triggerAlienAbduction(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.7f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3👽 §4🚨 ЗАФИКСИРОВАНО ПОХИЩЕНИЕ! Координаты: " + coords);
        
        plugin.getTelegramManager().broadcastEvent("Похищение Пришельцами", coords, "Неизвестно");
    }

    private void triggerMutantSighting(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_ZOMBIE_AMBIENT, 1.0f, 0.8f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3🧬 §cМутант замечен в " + coords + "! Осторожно!");
        
        plugin.getTelegramManager().broadcastEvent("Наблюдение Мутанта", coords, "Неизвестно");
    }

    private void triggerShadowCreature(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_WARDEN_AMBIENT, 0.5f, 1.2f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3🌑 §8Теневое существо в " + coords + "! Не приближаться!");
        
        plugin.getTelegramManager().broadcastEvent("Теневое Существо", coords, "Неизвестно");
    }

    private void triggerStrangeSounds(Location location) {
        location.getWorld().playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.3f, 0.5f);
        Bukkit.broadcastMessage("§3🔊 §bСлышны странные звуки... Шепот из ниоткуда...");
    }

    private void triggerEMFReading(Location location) {
        Player nearest = getNearestPlayer(location);
        if (nearest != null) {
            nearest.playSound(nearest.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 2.0f);
            int strength = random.nextInt(5) + 1;
            nearest.sendMessage("§3📡 §bЭМП сигнал: §e" + strength + "/5§b. Источник рядом...");
            
            if (strength >= 4) {
                String telegramMessage = "📡 Сильный ЭМП сигнал (" + strength + "/5) в " + 
                    location.getBlockX() + ", " + location.getBlockZ() + ". Возможно что-то крупное.";
                plugin.getTelegramManager().sendToTelegram(telegramMessage);
            }
        }
    }

    private void triggerCrypticMessage() {
        String message = crypticMessages.getRandomMessage();
        Bukkit.broadcastMessage(message);
        String cleanMessage = message.replace("§8[ШЕПОТ] §7", "");
        plugin.getTelegramManager().sendToTelegram("💬 Криптическое сообщение: " + cleanMessage);
    }

    private void triggerWhispersInMind(Player player) {
        String[] whispers = {
            "Беги... пока не поздно...",
            "Они в твоей голове...",
            "Ты следующий...",
            "Не смотри behind you...",
            "Мы уже здесь...",
            "Кровь... нужно больше крови...",
            "Твои воспоминания ложны...",
            "Проснись...",
            "Они идут за тобой...",
            "Не доверяй зеркалам..."
        };
        
        String whisper = whispers[random.nextInt(whispers.length)];
        player.sendTitle("§8⚡", "§7" + whisper, 10, 60, 20);
        player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, 0.8f, 0.5f);
        player.sendMessage("§8[ШЕПОТ В РАЗУМЕ] §7" + whisper);
    }

    private void triggerShadowFigure(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_WARDEN_SNIFF, 1.0f, 0.8f);
        
        Bukkit.broadcastMessage("§8🌑 §0Теневая фигура замечена... Она наблюдает...");
    }

    private void triggerTimeAnomaly(Player player) {
        player.sendTitle("§e⏰", "§6Аномалия времени обнаружена", 10, 40, 10);
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1.0f, 0.3f);
        
        Location newLoc = player.getLocation().add(
            random.nextInt(11) - 5,
            0,
            random.nextInt(11) - 5
        );
        player.teleport(newLoc);
        
        player.sendMessage("§e⏰ §6Пространственно-временная аномалия! Вы были перемещены.");
    }

    private void triggerEntityStare(Player player) {
        player.sendTitle("§c👁", "§4Кто-то смотрит на тебя", 10, 60, 20);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.3f, 2.0f);
        
        for (int i = 0; i < 8; i++) {
            Location soundLoc = player.getLocation().add(
                random.nextInt(21) - 10,
                random.nextInt(5),
                random.nextInt(21) - 10
            );
            player.getWorld().playSound(soundLoc, Sound.ENTITY_CREEPER_PRIMED, 0.1f, 0.5f);
        }
    }

    private void triggerGravityAnomaly(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_IRON_GOLEM_DAMAGE, 1.0f, 0.1f);
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(location) < 15) {
                player.setVelocity(player.getVelocity().setY(1.5));
                player.sendMessage("§e⚡ §6Гравитационная аномалия! Вы подброшены в воздух!");
            }
        }
    }

    private void triggerMemoryLoss(Player player) {
        player.sendTitle("§d💫", "§5Провалы в памяти...", 10, 50, 20);
        player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_DEACTIVATE, 1.0f, 0.8f);
        
        if (random.nextBoolean()) {
            player.getInventory().remove(Material.BREAD);
            player.getInventory().remove(Material.APPLE);
            player.sendMessage("§d💫 §5Вы не помните, куда дели еду...");
        }
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
