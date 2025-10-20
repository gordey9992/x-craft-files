package com.github.xcraftfiles.managers;

import com.github.xcraftfiles.XCraftFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final XCraftFiles plugin;
    private FileConfiguration messagesConfig;
    private FileConfiguration investigationsConfig;
    private File messagesFile;
    private File investigationsFile;

    public ConfigManager(XCraftFiles plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Сохраняем конфиги по умолчанию
        plugin.saveDefaultConfig();
        saveDefaultConfig("messages.yml");
        saveDefaultConfig("investigations.yml");
        
        // Загружаем конфиги
        messagesConfig = loadConfig("messages.yml");
        investigationsConfig = loadConfig("investigations.yml");
        
        plugin.getLogger().info("✅ Конфигурации загружены");
    }

    private void saveDefaultConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    private FileConfiguration loadConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getInvestigationsConfig() {
        return investigationsConfig;
    }

    public void saveMessagesConfig() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("❌ Не удалось сохранить messages.yml: " + e.getMessage());
        }
    }
}
