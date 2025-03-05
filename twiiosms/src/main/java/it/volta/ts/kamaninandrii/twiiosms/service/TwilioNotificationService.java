package it.volta.ts.kamaninandrii.twiiosms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import it.volta.ts.kamaninandrii.twiiosms.config.TwilioConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TwilioNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioNotificationService.class);
    private final TwilioConfig twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public String sendSms(String phoneNumber, String message, String id, String latitude, String longitude, String userId, String status) {
        // Формируем текст сообщения с дополнительной информацией
        String formattedMessage = String.format(
                "ID: %s\nСообщение: %s\nКоординаты: Широта - %s, Долгота - %s\nПользователь: %s\nСтатус: %s",
                id, message, latitude, longitude, userId, status
        );

        try {
            Message sms = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    formattedMessage
            ).create();

            logger.info("SMS отправлено успешно. SID: {}", sms.getSid());
            return "SMS отправлено успешно";
        } catch (Exception e) {
            logger.error("Не удалось отправить SMS", e);
            return "Ошибка: " + e.getMessage();
        }
    }

    public String sendWhatsAppMessage(String phoneNumber, String message, String id, String latitude, String longitude, String userId, String status) {
        // Формируем текст сообщения с дополнительной информацией
        String formattedMessage = String.format(
                "ID: %s\nСообщение: %s\nКоординаты: Широта - %s, Долгота - %s\nПользователь: %s\nСтатус: %s",
                id, message, latitude, longitude, userId, status
        );

        try {
            Message whatsappMessage = Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber("whatsapp:" + twilioConfig.getPhoneNumber()),
                    formattedMessage
            ).create();

            logger.info("WhatsApp сообщение отправлено успешно. SID: {}", whatsappMessage.getSid());
            return "WhatsApp сообщение отправлено успешно";
        } catch (Exception e) {
            logger.error("Не удалось отправить WhatsApp сообщение", e);
            return "Ошибка: " + e.getMessage();
        }
    }
}