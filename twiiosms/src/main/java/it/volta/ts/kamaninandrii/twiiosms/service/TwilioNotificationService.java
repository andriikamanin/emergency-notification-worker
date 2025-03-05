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

    public String sendSms(String phoneNumber, String message) {
        try {
            Message sms = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    message
            ).create();

            logger.info("SMS sent successfully. SID: {}", sms.getSid());
            return "SMS sent successfully";
        } catch (Exception e) {
            logger.error("Failed to send SMS", e);
            return "Error: " + e.getMessage();
        }
    }

    public String sendWhatsAppMessage(String phoneNumber, String message) {
        try {
            Message whatsappMessage = Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber("whatsapp:" + twilioConfig.getPhoneNumber()),
                    message
            ).create();

            logger.info("WhatsApp message sent successfully. SID: {}", whatsappMessage.getSid());
            return "WhatsApp message sent successfully";
        } catch (Exception e) {
            logger.error("Failed to send WhatsApp message", e);
            return "Error: " + e.getMessage();
        }
    }
}
