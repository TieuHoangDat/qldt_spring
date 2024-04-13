package com.ptit.qldt;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class bot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    public bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    public void sendMesseage(String text, String userIdTelegram) {
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(userIdTelegram)
                .text(text)
                .build();
        try {
            telegramClient.execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            System.out.println(message_text);
            long chat_id = update.getMessage().getChatId();
            System.out.println(chat_id);

            if (message_text.equals("/start")) {
                message_text = "Chào mừng bạn đến máy chủ hỗ trợ học tập cho sinh viên\nUserId của bạn là: "+chat_id+"\nHãy vào thông tin cá nhân để cập nhật userIdTelegram có thể nhận được thông báo lịch học";
            }

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }
}