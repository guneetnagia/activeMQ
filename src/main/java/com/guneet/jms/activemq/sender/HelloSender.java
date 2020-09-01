package com.guneet.jms.activemq.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guneet.jms.activemq.config.JmsConfig;
import com.guneet.jms.activemq.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
//        System.out.println("I am sending message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("New Message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);
//        System.out.println("Message Sent");
    }


    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Reply Back")
                .build();

        Message recievedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMsg = null;
                try {
                    helloMsg = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMsg.setStringProperty("_type","com.guneet.jms.activemq.model.HelloWorldMessage");
                    System.out.println("hello");
                    return helloMsg;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Boom");
                }
            }
        });
        System.out.println(recievedMsg.getBody(HelloWorldMessage.class));
    }
}
