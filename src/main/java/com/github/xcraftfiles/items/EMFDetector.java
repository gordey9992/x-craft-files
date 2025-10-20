package com.github.xcraftfiles.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EMFDetector {
    
    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.STICK); // Заглушка
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§fEMFDetector");
            meta.setLore(Arrays.asList("§7Предмет для расследований", "§8X-Craft Files"));
            item.setItemMeta(meta);
        }
        return item;
    }
}
