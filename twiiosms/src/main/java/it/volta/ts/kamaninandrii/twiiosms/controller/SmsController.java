package it.volta.ts.kamaninandrii.twiiosms.controller;

import it.volta.ts.kamaninandrii.twiiosms.service.TwilioNotificationService;
import lombok.RequiredArgsConstructor;
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

        if (phoneNumber == null || message == null || phoneNumber.isBlank() || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Phone number and message are required"));
        }

        String response = smsService.sendWhatsAppMessage(phoneNumber, message);
        return ResponseEntity.ok(Map.of("status", response));
    }
}
