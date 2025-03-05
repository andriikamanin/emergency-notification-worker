package it.volta.ts.kamaninandrii.twiiosms.controller;

import it.volta.ts.kamaninandrii.twiiosms.service.TwilioNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final TwilioNotificationService smsService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendSms(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String message = request.get("message");
        String id = request.get("id");
        String latitude = request.get("latitude");
        String longitude = request.get("longitude");
        String userId = request.get("userId");
        String status = request.get("status");

        if (phoneNumber == null || message == null || phoneNumber.isBlank() || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Phone number and message are required"));
        }

        String response = smsService.sendSms(phoneNumber, message, id, latitude, longitude, userId, status);
        return ResponseEntity.ok(Map.of("status", response));
    }

    @PostMapping("/send-whatsapp")
    public ResponseEntity<Map<String, String>> sendWhatsAppMessage(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String message = request.get("message");
        String id = request.get("id");
        String latitude = request.get("latitude");
        String longitude = request.get("longitude");
        String userId = request.get("userId");
        String status = request.get("status");

        if (phoneNumber == null || message == null || phoneNumber.isBlank() || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Phone number and message are required"));
        }

        String response = smsService.sendWhatsAppMessage(phoneNumber, message, id, latitude, longitude, userId, status);
        return ResponseEntity.ok(Map.of("status", response));
    }
}