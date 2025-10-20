package com.github.xcraftfiles.managers;

import com.github.xcraftfiles.XCraftFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TelegramManager {
    private final XCraftFiles plugin;
    private final String botToken;
    private final String chatId;
    private final int threadId;
    private boolean enabled;

    public TelegramManager(XCraftFiles plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.botToken = config.getString("telegram.bot-token", "8263486505:AAHs0FDTQJrNRoMd1loLaNASWrp4SfKEuRc");
        this.chatId = config.getString("telegram.chat-id", "-1002935953089");
        this.threadId = config.getInt("telegram.thread-id", 434);
        this.enabled = config.getBoolean("telegram.enabled", true);
    }

    public void initialize() {
        if (enabled) {
            plugin.getLogger().info("📱 Telegram менеджер инициализирован");
        }
    }

    public void sendToTelegram(String message) {
        if (!enabled) return;

        new Thread(() -> {
            try {
                String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonPayload = String.format(
                    "{\"chat_id\": \"%s\", \"message_thread_id\": %d, \"text\": \"%s\"}",
                    chatId, threadId, escapeJsonString(message)
                );

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    plugin.getLogger().warning("❌ Ошибка Telegram: " + responseCode);
                }
                
                conn.disconnect();
            } catch (Exception e) {
                plugin.getLogger().warning("❌ Ошибка отправки в Telegram: " + e.getMessage());
            }
        }).start();
    }

    private String escapeJsonString(String value) {
        return value.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    public void broadcastEvent(String eventType, String location, String playerName) {
        String message = "🚨 ПАРАНОРМАЛЬНОЕ СОБЫТИЕ\n" +
                        "Тип: " + eventType + "\n" +
                        "Местоположение: " + location + "\n" +
                        "Агент: " + (playerName != null ? playerName : "Неизвестно") + "\n" +
                        "Время: " + java.time.LocalDateTime.now() + "\n\n" +
                        "Требуется немедленное расследование!";
        sendToTelegram(message);
    }
}
