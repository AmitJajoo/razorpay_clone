package com.amit.razorpay.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class DateTimeConfig {

    @Bean
    public Clock applicationClock(
            @Value("${app.timezone}") String timezone) {

        return Clock.system(ZoneId.of(timezone));
    }
}
