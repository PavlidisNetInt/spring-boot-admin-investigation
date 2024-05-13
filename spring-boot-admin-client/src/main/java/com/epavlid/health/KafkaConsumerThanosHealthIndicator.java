package com.epavlid.health;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * This will define an actuator named "kafkaConsumerThanos"
 */
@Slf4j
@Component
@ConditionalOnEnabledHealthIndicator("kafkaThanos")
public class KafkaConsumerThanosHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");

            try (AdminClient adminClient = AdminClient.create(properties)) {
                DescribeClusterResult describeClusterResult = adminClient.describeCluster();
                describeClusterResult.nodes().get();
            }

            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }
}