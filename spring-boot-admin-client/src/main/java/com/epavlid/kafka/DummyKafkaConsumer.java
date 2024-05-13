package com.epavlid.kafka;

import com.epavlid.service.DummyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@KafkaListener(groupId = "${dummy.client.consumer.group-id}", topics = "${dummy.client.consumer.dummy-topic}", containerFactory = "kafkaListenerContainerFactory")
@Slf4j
@Component
@RequiredArgsConstructor
public class DummyKafkaConsumer {

    private final DummyService dummyService;

    @KafkaHandler
    public void consumeEvent(String dummyEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Listened to an event on topic: {} with payload: {}", topic, dummyEvent);
        dummyService.dummyFun();
    }

}
