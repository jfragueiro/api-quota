package com.vicarius.apiquota.event.producer.impl;


import com.vicarius.apiquota.event.producer.dto.UserConsumeQuotaEvent;
import com.vicarius.apiquota.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserConsumeQuotaEventImpl extends ProducerEventImpl<UserConsumeQuotaEvent> {
    private static final String EVENT_NAME = "USER_CONSUME_QUOTA_EVENT";

    public UserConsumeQuotaEventImpl(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopic() {
        return Topics.USER_CONSUME_QUOTA_EVENT;
    }

    @Override
    protected String getEventName() {
        return EVENT_NAME;
    }
}
