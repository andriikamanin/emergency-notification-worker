package it.volta.ts.kamaninandrii.twiiosms.config;

import it.volta.ts.kamaninandrii.twiiosms.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, Notification> consumerFactory() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Используем JsonDeserializer с типом Notification
        JsonDeserializer<Notification> jsonDeserializer = new JsonDeserializer<>(Notification.class);
        jsonDeserializer.setRemoveTypeHeaders(false);  // Убираем заголовки типов, если нужно
        jsonDeserializer.addTrustedPackages("it.volta.ts.kamaninandrii.twiiosms.model");

        // Обработчик ошибок
        ErrorHandlingDeserializer<Notification> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        // Возвращаем фабрику потребителей с десериализатором и ключом String
        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Notification> messageListenerContainer() {
        // Настройка партиции и смещения
        TopicPartitionOffset topicPartitionOffset = new TopicPartitionOffset("emergency-notifications", 0);

        // Создание настроек контейнера с топиком и партицией
        ContainerProperties containerProperties = new ContainerProperties(topicPartitionOffset);

        // Устанавливаем режим подтверждения сообщений вручную
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // Создание контейнера с фабрикой потребителей и параметрами контейнера
        ConcurrentMessageListenerContainer<String, Notification> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProperties);

        // Устанавливаем обработчик сообщений
        container.getContainerProperties().setMessageListener(new MessageListener<String, Notification>() {
            @Override
            public void onMessage(ConsumerRecord<String, Notification> record) {
                Notification message = record.value();
                System.out.println("Получено сообщение: " + message);

                // Обработка уведомления
                try {
                    processNotification(message);

                    // Получаем объект Acknowledgment и подтверждаем сообщение
                    Acknowledgment acknowledgment = (Acknowledgment) record.headers().lastHeader("ack");
                    if (acknowledgment != null) {
                        acknowledgment.acknowledge();  // Ручное подтверждение
                    }
                } catch (Exception e) {
                    // Логирование ошибки
                    System.err.println("Ошибка при обработке сообщения: " + e.getMessage());
                }
            }
        });

        return container;
    }

    // Пример метода для обработки уведомления
    private void processNotification(Notification notification) {
        // Здесь ваша логика обработки уведомлений
        System.out.println("Обрабатываем уведомление: " + notification);
    }
}