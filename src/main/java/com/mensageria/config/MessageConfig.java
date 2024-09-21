package com.mensageria.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.PubsubMessage;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.mensageria.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class MessageConfig {
	
    private final MessageService messageService;

    public MessageConfig(MessageService messageService) {
        this.messageService = messageService;
    }

    @Bean
    public Subscriber subscriber() {
        String projectId = "serjava-demo";
        String subscriptionId = "pimhui-sub";
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
        
        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
                String messageData = message.getData().toStringUtf8();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> payload;
                try {
                    payload = objectMapper.readValue(messageData, new TypeReference<Map<String, Object>>() {});
                    messageService.processMessage(payload);
                   // consumer.ack(); 
                } catch (IOException e) {
                   // consumer.nack();
                }
            
        };
        
        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();

        return subscriber;
    }


}
