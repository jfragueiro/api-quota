package com.vicarius.apiquota.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.vicarius.apiquota.event.Topics.USER_CONSUME_QUOTA_EVENT;

@Configuration
public class KafkaConfig
{
    @Value("${spring.kafka.replicas}")
    private int replicas;

    @Bean
    public NewTopic USER_CONSUME_QUOTA_EVENT()
    {
        return TopicBuilder.name(USER_CONSUME_QUOTA_EVENT).replicas(replicas).partitions(3).build();
    }
}
