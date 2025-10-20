package com.github.xcraftfiles.commands;

import com.github.xcraftfiles.XCraftFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
    private final XCraftFiles plugin;
    
    public CommandManager(XCraftFiles plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§3🛸 §bX-Craft Files v1.0");
            sender.sendMessage("§b/xfiles reload §7- Перезагрузить конфигурацию");
            sender.sendMessage("§b/xfiles status §7- Статус плагина");
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                plugin.getConfigManager().loadConfigs();
                sender.sendMessage("§3🛸 §bКонфигурация перезагружена!");
                break;
            case "status":
                sender.sendMessage("§3🛸 §bПлагин активен. Правда где-то рядом...");
                break;
            default:
                sender.sendMessage("§cНеизвестная команда. Используйте /xfiles для помощи");
        }
        return true;
    }
}
