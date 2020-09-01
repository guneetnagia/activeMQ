package com.guneet.jms.activemq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter convertor = new MappingJackson2MessageConverter();
        convertor.setTargetType(MessageType.TEXT);
        convertor.setTypeIdPropertyName("_type");
        return convertor;
    }
}
