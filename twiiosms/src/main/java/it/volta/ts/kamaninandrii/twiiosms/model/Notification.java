package it.volta.ts.kamaninandrii.twiiosms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String id; // Уникальный идентификатор уведомления
    private String phoneNumber; // Номер телефона получателя
    private String message; // Содержание сообщения
    private String latitude; // Широта (для добавления в сообщение)
    private String longitude; // Долгота (для добавления в сообщение)
    private String userId; // Идентификатор пользователя, которому отправляется уведомление
    private String status; // Статус уведомления (например, прочитано, не прочитано)
    private NotificationPriority priority; // Приоритет уведомления
    private NotificationType type; // Тип уведомления (SMS, EMAIL, PUSH и т.д.)
    private long timestamp; // Время создания уведомления
}