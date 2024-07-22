package com.example.api.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration

@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {
  @Value("${twilio.account-sid}")
    String accountSid;
    @Value("${twilio.auth-token}")
    String authToken;
    @Value("${twilio.twilio-phone-number}")
    String twilioPhoneNumber;
    @Value("${twilio.service-sid}")
    String serviceSid;

}