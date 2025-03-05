package it.volta.ts.kamaninandrii.twiiosms.service;

import it.volta.ts.kamaninandrii.twiiosms.model.NotificationResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationResultListener {

    @KafkaListener(topics = "notification-results", groupId = "notification-result-group")
    public void listenForNotificationResult(NotificationResult result) {
        // Логика обработки результата
        if ("Success".equals(result.getStatus())) {
            System.out.println("Уведомление " + result.getNotificationId() + " успешно обработано");
        } else {
            System.out.println("Ошибка при обработке уведомления " + result.getNotificationId() + ": " + result.getErrorMessage());
        }
    }
}