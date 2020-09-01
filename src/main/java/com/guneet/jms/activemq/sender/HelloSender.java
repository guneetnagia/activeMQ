package com.guneet.jms.activemq.sender;

import com.guneet.jms.activemq.config.JmsConfig;
import com.guneet.jms.activemq.model.HelloWorldMessage;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        System.out.println("I am sending message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("New Message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);
        System.out.println("Message Sent");
    }
}
