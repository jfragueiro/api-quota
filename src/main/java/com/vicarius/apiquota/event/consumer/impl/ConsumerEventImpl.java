package com.vicarius.apiquota.event.consumer.impl;


import com.vicarius.apiquota.common.LoggerNewRelic;
import com.vicarius.apiquota.event.consumer.ConsumerEvent;
import com.vicarius.apiquota.exception.ConsumerEventException;
import com.vicarius.apiquota.util.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConsumerEventImpl<T> implements ConsumerEvent {
    private static final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    protected void processMessage(String message) {
        String consumerTopic = this.getTopic();
        String eventName = this.getEventName();
        try {
            T dto = objectMapper.readValue(message, this.getDtoClass());
            log.debug("Receiving message. Event: {}| topic={}. Object: {}", eventName, consumerTopic, dto);
            this.callService(dto);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            String exceptionMessage = String.format("Error processing consumer-topic %s with message %s from event",
                    consumerTopic, message);
            ConsumerEventException ex = new ConsumerEventException(exceptionMessage, e);
            LoggerNewRelic.logAndNotifyError(exceptionMessage, ex, log);
        }
    }

    protected abstract void callService(T dto);

    protected abstract String getTopic();

    protected abstract Class<T> getDtoClass();

    protected abstract String getEventName();
}
