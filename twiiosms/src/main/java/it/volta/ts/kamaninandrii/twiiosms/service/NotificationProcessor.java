package it.volta.ts.kamaninandrii.twiiosms.service;

import it.volta.ts.kamaninandrii.twiiosms.model.Notification;
import it.volta.ts.kamaninandrii.twiiosms.model.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationProcessor {

    private final TwilioNotificationService twilioNotificationService;

    @Autowired
    public NotificationProcessor(TwilioNotificationService twilioNotificationService) {
        this.twilioNotificationService = twilioNotificationService;
    }

    public void processNotification(Notification notification) {
        // Определяем тип уведомления и обрабатываем его соответствующим образом
        NotificationType type = notification.getType();
        switch (type) {
            case SMS:
                sendSmsNotification(notification);
                break;
            case WHATSAPP:
                sendWhatsAppNotification(notification);
                break;
            case EMAIL:
                // Можно добавить логику для обработки EMAIL уведомлений
                break;
            default:
                System.out.println("Неизвестный тип уведомления: " + type);
        }
    }

    private void sendSmsNotification(Notification notification) {
        String phoneNumber = notification.getPhoneNumber();
        String message = notification.getMessage();
        // Логика для отправки SMS
        twilioNotificationService.sendSms(phoneNumber, message, notification.getId(), notification.getLatitude(), notification.getLongitude(), notification.getUserId(), notification.getStatus());
    }

    private void sendWhatsAppNotification(Notification notification) {
        String phoneNumber = notification.getPhoneNumber();
        String message = notification.getMessage();
        // Логика для отправки WhatsApp сообщения
        twilioNotificationService.sendWhatsAppMessage(phoneNumber, message, notification.getId(), notification.getLatitude(), notification.getLongitude(), notification.getUserId(), notification.getStatus());
    }
}