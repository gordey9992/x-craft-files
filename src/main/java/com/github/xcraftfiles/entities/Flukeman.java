package com.github.xcraftfiles.entities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flukeman {
    
    public static void applyEffects(LivingEntity entity) {
        // Здесь будут особые эффекты для Flukeman
        // Например:
        // entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
    }
    
    public static void onDamage(EntityDamageEvent event) {
        // Особое поведение при получении урона
    }
}
