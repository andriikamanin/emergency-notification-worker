package it.volta.ts.kamaninandrii.twiiosms.model;

public class NotificationResult {
    private String notificationId;
    private String status;
    private String errorMessage;

    public NotificationResult(String notificationId, String status) {
        this.notificationId = notificationId;
        this.status = status;
    }

    public NotificationResult(String notificationId, String status, String errorMessage) {
        this.notificationId = notificationId;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}