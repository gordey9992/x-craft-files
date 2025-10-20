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

        // Запускаем редкие страшные события каждые 5-10 минут
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
        if (chance < 5) { // 5% шанс на страшное событие
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
        world.playSound(location, Sound.minecraft:entity.lightning_bolt.thunder, 1.0f, 0.5f);
        world.spawnParticle(Particle.minecraft:firework, location, 100, 10, 20, 10);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        String message = "👽 НЛО замечено в секторе " + coords + "! ТРЕВОГА!";
        Bukkit.broadcastMessage("§3👽 §b" + message);
        
        plugin.getTelegramManager().broadcastEvent("НЛО Наблюдение", coords, null, 
            "Зафиксирован неопознанный летающий объект. Высокая энергетическая сигнатура.");
    }

    private void triggerGhostActivity(Location location) {
        World world = location.getWorld();
        // Заменяем ENTITY_GHOST_AMBIENT на существующие звуки
        world.playSound(location, Sound.minecraft:entity.phantom.ambient, 1.0f, 0.8f);
        world.playSound(location, Sound.minecraft:entity.vex.ambient, 0.8f, 0.5f);
        world.playSound(location, Sound.minecraft:block.conduit.ambient, 0.6f, 1.2f);
        world.spawnParticle(Particle.minecraft:soul, location, 50, 3, 3, 3);
        world.spawnParticle(Particle.minecraft:soul_fire_flame, location, 30, 2, 2, 2);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3👻 §bПаранормальная активность в " + coords + ". ЭМП зашкаливает!");
        
        plugin.getTelegramManager().broadcastEvent("Паранормальная Активность", coords, null,
            "Аномальные показатели ЭМП, понижение температуры, визуальные аномалии.");
    }

    private void triggerAlienAbduction(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.minecraft:entity.enderman.teleport, 1.0f, 0.7f);
        world.playSound(location, Sound.minecraft:block.beacon.ambient, 0.8f, 1.5f);
        world.spawnParticle(Particle.minecraft:portal, location, 100, 2, 2, 2);
        world.spawnParticle(Particle.minecraft:reverse_portal, location, 50, 3, 3, 3);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3👽 §4🚨 ЗАФИКСИРОВАНО ПОХИЩЕНИЕ! Координаты: " + coords);
        
        plugin.getTelegramManager().broadcastEvent("Похищение Пришельцами", coords, null,
            "Зафиксирован луч похищения. Возможно, пропали местные жители.");
    }

    private void triggerMutantSighting(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.minecraft:entity.zombie.ambient, 1.0f, 0.8f);
        world.playSound(location, Sound.minecraft:entity.hoglin.angry, 0.7f, 1.2f);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3🧬 §cМутант замечен в " + coords + "! Осторожно!");
        
        plugin.getTelegramManager().broadcastEvent("Наблюдение Мутанта", coords, null,
            "Гуманоидное существо с аномальными физическими характеристиками.");
    }

    private void triggerShadowCreature(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.minecraft:entity.warden.ambient, 0.5f, 1.2f);
        world.playSound(location, Sound.minecraft:entity.warden.angry, 0.3f, 1.5f);
        world.spawnParticle(Particle.minecraft:squid_ink, location, 30, 2, 2, 2);
        world.spawnParticle(Particle.minecraft:large_smoke, location, 20, 3, 3, 3);
        
        String coords = location.getBlockX() + ", " + location.getBlockZ();
        Bukkit.broadcastMessage("§3🌑 §8Теневое существо в " + coords + "! Не приближаться!");
        
        plugin.getTelegramManager().broadcastEvent("Теневое Существо", coords, null,
            "Существо, состоящее из чистой тьмы. Поглощает свет и звук вокруг себя.");
    }

    private void triggerStrangeSounds(Location location) {
        // Заменяем ENTITY_ELDER_GUARDIAN_AMBIENT на другие звуки
        location.getWorld().playSound(location, Sound.minecraft:entity.ender_dragon.growl, 0.3f, 0.5f);
        location.getWorld().playSound(location, Sound.minecraft:entity.vex.ambient, 0.5f, 0.3f);
        location.getWorld().playSound(location, Sound.minecraft:entity.warden.roar, 0.2f, 0.8f);
        Bukkit.broadcastMessage("§3🔊 §bСлышны странные звуки... Шепот из ниоткуда...");
    }

    private void triggerEMFReading(Location location) {
        Player nearest = getNearestPlayer(location);
        if (nearest != null) {
            nearest.playSound(nearest.getLocation(), Sound.minecraft:block.note_block.bit, 1.0f, 2.0f);
            int strength = random.nextInt(5) + 1;
            nearest.sendMessage("§3📡 §bЭМП сигнал: §e" + strength + "/5§b. Источник рядом...");
            
            if (strength >= 4) {
                plugin.getTelegramManager().sendToTelegram("📡 Сильный ЭМП сигнал (" + strength + "/5) в " + 
                    location.getBlockX() + ", " + location.getBlockZ() + ". Возможно что-то крупное.");
            }
        }
    }

    private void triggerCrypticMessage() {
        String message = crypticMessages.getRandomMessage();
        Bukkit.broadcastMessage(message);
        plugin.getTelegramManager().sendToTelegram("💬 Криптическое сообщение: " + message.replace("§8[ШЕПОТ] §7", ""));
    }

    // СТРАШНЫЕ СОБЫТИЯ
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
        player.playSound(player.getLocation(), Sound.minecraft:entity.warden.heartbeat, 0.8f, 0.5f);
        player.sendMessage("§8[ШЕПОТ В РАЗУМЕ] §7" + whisper);
    }

    private void triggerShadowFigure(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.minecraft:entity.warden.sniff, 1.0f, 0.8f);
        world.spawnParticle(Particle.minecraft:soul, location, 100, 2, 3, 2);
        world.spawnParticle(Particle.minecraft:large_smoke, location, 50, 3, 4, 3);
        
        Bukkit.broadcastMessage("§8🌑 §0Теневая фигура замечена... Она наблюдает...");
    }

    private void triggerTimeAnomaly(Player player) {
        player.sendTitle("§e⏰", "§6Аномалия времени обнаружена", 10, 40, 10);
        player.playSound(player.getLocation(), Sound.minecraft:block.bell.use, 1.0f, 0.3f);
        
        // Случайный телепорт на несколько блоков
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
        player.playSound(player.getLocation(), Sound.minecraft:entity.ender_dragon.growl, 0.3f, 2.0f);
        
        // Создаем звуки вокруг игрока
        for (int i = 0; i < 8; i++) {
            Location soundLoc = player.getLocation().add(
                random.nextInt(21) - 10,
                random.nextInt(5),
                random.nextInt(21) - 10
            );
            player.getWorld().playSound(soundLoc, Sound.minecraft:entity.creeper.primed, 0.1f, 0.5f);
        }
    }

    private void triggerGravityAnomaly(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.minecraft:entity.iron_golem.damage, 1.0f, 0.1f);
        
        // Подбрасываем ближайших игроков
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(location) < 15) {
                player.setVelocity(player.getVelocity().setY(1.5));
                player.sendMessage("§e⚡ §6Гравитационная аномалия! Вы подброшены в воздух!");
            }
        }
    }

    private void triggerMemoryLoss(Player player) {
        player.sendTitle("§d💫", "§5Провалы в памяти...", 10, 50, 20);
        player.playSound(player.getLocation(), Sound.minecraft:block.conduit.deactivate, 1.0f, 0.8f);
        
        // Случайно очищаем инвентарь (не важные предметы)
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
