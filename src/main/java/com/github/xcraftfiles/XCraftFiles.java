package com.github.xcraftfiles;

import com.github.xcraftfiles.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public class XCraftFiles extends JavaPlugin {
    private static XCraftFiles instance;
    private ConfigManager configManager;
    private EventManager eventManager;
    private InvestigationManager investigationManager;
    private TelegramManager telegramManager;
    private RewardManager rewardManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Инициализация менеджеров
        this.configManager = new ConfigManager(this);
        this.eventManager = new EventManager(this);
        this.investigationManager = new InvestigationManager(this);
        this.telegramManager = new TelegramManager(this);
        this.rewardManager = new RewardManager(this);
        
        // Загрузка конфигураций
        configManager.loadConfigs();
        telegramManager.initialize();
        eventManager.startEventScheduler();
        investigationManager.loadCases();
        
        getLogger().info("🛸 X-Craft Files активирован. Правда где-то рядом...");
        telegramManager.sendToTelegram("🔮 Плагин X-Craft Files активирован на сервере. Начинаем наблюдение...");
    }

    @Override
    public void onDisable() {
        telegramManager.sendToTelegram("🔮 Плагин X-Craft Files деактивирован. Но правда все еще там...");
        getLogger().info("🛸 X-Craft Files деактивирован.");
    }

    public static XCraftFiles getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() { return configManager; }
    public EventManager getEventManager() { return eventManager; }
    public InvestigationManager getInvestigationManager() { return investigationManager; }
    public TelegramManager getTelegramManager() { return telegramManager; }
    public RewardManager getRewardManager() { return rewardManager; }
}
