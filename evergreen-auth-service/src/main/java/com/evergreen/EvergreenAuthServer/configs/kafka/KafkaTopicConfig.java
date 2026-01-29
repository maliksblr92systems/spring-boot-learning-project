package com.evergreen.EvergreenAuthServer.configs.kafka;

import java.util.HashMap;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String RETENTION_MS = String.valueOf(7 * 24 * 60 * 60 * 1000); // 7 days

    // Standard topic config
    public HashMap<String, String> getStandardTopicConfig() {
        HashMap<String, String> standardConfig = new HashMap<>();
        standardConfig.put("min.insync.replicas", "2");
        standardConfig.put("cleanup.policy", "delete");
        standardConfig.put("retention.ms", RETENTION_MS);
        return standardConfig;
    }

    // Helper method to create a topic using the standard config
    private NewTopic createTopicWithStandardConfig(String name, int partitions, int replicas) {
        TopicBuilder builder = TopicBuilder.name(name).partitions(partitions).replicas(replicas);
        // Apply standard config
        getStandardTopicConfig().forEach(builder::config);

        return builder.build();
    }

    // Topics
    @Bean
    public NewTopic paymentTopic() {
        return createTopicWithStandardConfig("payment-topic", 2, 2);
    }

    @Bean
    public NewTopic userActivityTopic() {
        return createTopicWithStandardConfig("user-activity-topic", 2, 2);
    }
}
