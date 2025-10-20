package com.github.xcraftfiles.managers;

import com.github.xcraftfiles.XCraftFiles;

public class InvestigationManager {
    private final XCraftFiles plugin;
    
    public InvestigationManager(XCraftFiles plugin) {
        this.plugin = plugin;
    }
    
    public void loadCases() {
        plugin.getLogger().info("✅ Дела для расследований загружены");
    }
}
