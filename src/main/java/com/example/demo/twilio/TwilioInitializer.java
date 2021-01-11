package com.example.demo.twilio;


import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TwilioInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;

        Twilio.init(
              twilioConfiguration.getAccount_sid(),
              twilioConfiguration.getAuth_token()
        );

        LOGGER.info("Twilio Initialized .... with SID()", twilioConfiguration.getAccount_sid());
    }
}
