package com.epavlid.health;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * This will define an actuator named "kafkaConsumerAbstract"
 */
@Component
@RequiredArgsConstructor
@ConditionalOnEnabledHealthIndicator("abstract")
public class KafkaConsumerAbstractHealthIndicator extends AbstractHealthIndicator {

    private final AdminClient adminClient;

    @Override
    protected void doHealthCheck(Health.Builder builder){
        try {
            adminClient.describeCluster().nodes().get();
            builder.up();
        } catch (InterruptedException ie) {
            builder.down().withException(ie);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            builder.down().withException(e);
        }
    }
}
