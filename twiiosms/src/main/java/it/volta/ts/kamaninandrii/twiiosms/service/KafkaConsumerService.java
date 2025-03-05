package it.volta.ts.kamaninandrii.twiiosms.service;

import it.volta.ts.kamaninandrii.twiiosms.model.Notification;
import it.volta.ts.kamaninandrii.twiiosms.model.NotificationResult; // Новый класс для результата обработки
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final String RESULT_TOPIC = "notification-results"; // Топик для ответов

    private final NotificationProcessor notificationProcessor;
    private final KafkaTemplate<String, NotificationResult> kafkaTemplate; // Для отправки результата

    @Autowired
    public KafkaConsumerService(NotificationProcessor notificationProcessor, KafkaTemplate<String, NotificationResult> kafkaTemplate) {
        this.notificationProcessor = notificationProcessor;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "emergency-notifications", groupId = "notification-group")
    public void consume(Notification notification) {
        try {
            // Обрабатываем уведомление
            notificationProcessor.processNotification(notification);

            // После успешной обработки отправляем результат
            NotificationResult result = new NotificationResult(notification.getId(), "Success");
            sendResultToKafka(result);
        } catch (Exception e) {
            // Логируем ошибку
            NotificationResult result = new NotificationResult(notification.getId(), "Failure", e.getMessage());
            sendResultToKafka(result);
        }
    }

    // Метод для отправки результата в Kafka
    private void sendResultToKafka(NotificationResult result) {
        kafkaTemplate.send(RESULT_TOPIC, result);
    }
}