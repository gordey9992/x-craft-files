package com.github.xcraftfiles.utils;

import java.util.Random;

public class CrypticMessages {
    private final String[] messages = {
        "§8[ШЕПОТ] §7Истина не хочет быть найденной...",
        "§8[ШЕПОТ] §7Они наблюдают... Всегда наблюдают...",
        "§8[ШЕПОТ] §7Не доверяйте никому. Особенно им...",
        "§8[ШЕПОТ] §7Круги на полях - это только начало...",
        "§8[ШЕПОТ] §7Синдикат знает о ваших действиях...",
        "§8[ШЕПОТ] §7Черная нефть... Она в нас...",
        "§8[ШЕПОТ] §7Проект 'Х' активен. Бегите пока не поздно...",
        "§8[ШЕПОТ] §7Мутанты среди нас... Вы их не узнаете...",
        "§8[ШЕПОТ] §7Правда изменит вас навсегда...",
        "§8[ШЕПОТ] §7Не смотрите на свет... Это ловушка..."
    };
    
    private final Random random = new Random();
    
    public String getRandomMessage() {
        return messages[random.nextInt(messages.length)];
    }
}
