package com.emailSender.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Value("${app.mail.concurrent-senders:5}")
    private int concurrentSenders;

    @Bean(name = "mailSenderExecutor")
    public Executor mailSenderExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(concurrentSenders);
        executor.setMaxPoolSize(concurrentSenders);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("mail-sender-");
        executor.initialize();
        return executor;
    }
}

