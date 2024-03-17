package com.vicarius.apiquota.event.consumer.impl;

import com.vicarius.apiquota.api.service.userQuota.QuotaService;
import com.vicarius.apiquota.event.Topics;
import com.vicarius.apiquota.event.consumer.dto.UserConsumeQuotaDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConsumeQuotaEvent extends ConsumerEventImpl<UserConsumeQuotaDto> {
    private static final String EVENT_NAME = "FintocResponseEventImpl";
    //here you should declarate the service which will process the event
    private final QuotaService service;

    @Override
    protected void callService(final UserConsumeQuotaDto dto) {
        // call the service in async way
        service.consumeQuota(dto.getUserId());
    }

    @Override
    protected String getTopic() {
        return Topics.USER_CONSUME_QUOTA_EVENT;
    }

    @Override
    protected Class<UserConsumeQuotaDto> getDtoClass() {
        return UserConsumeQuotaDto.class;
    }

    @Override
    protected String getEventName() {
        return EVENT_NAME;
    }

    @Override
    @KafkaListener(topics = Topics.USER_CONSUME_QUOTA_EVENT, groupId = "${spring.kafka.consumer.group-id}",
            autoStartup = "${spring.kafka.consumer.auto-start:false}", concurrency = "1")
    public void consume(final String message) {
        super.processMessage(message);
    }
}
