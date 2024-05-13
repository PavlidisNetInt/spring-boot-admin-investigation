package com.epavlid.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class KafkaConsumerSocketHealthIndicator implements HealthIndicator {

    private static final Long TIMEOUT = TimeUnit.SECONDS.toMillis(3);

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String kafkaServer;


    @Override
    public Health health() {
        try (Socket socket = new Socket()) {
            String[] serverParts = kafkaServer.split(":");
            socket.connect(new InetSocketAddress(serverParts[0], Integer.parseInt(serverParts[1])), TIMEOUT.intValue());
            return Health.up().build();
        } catch (final Exception e) {
            return Health.down(e).build();
        }
    }
}
