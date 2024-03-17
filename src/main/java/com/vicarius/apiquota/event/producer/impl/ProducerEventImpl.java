package com.vicarius.apiquota.event.producer.impl;


import com.vicarius.apiquota.common.LoggerNewRelic;
import com.vicarius.apiquota.event.producer.ProducerEvent;
import com.vicarius.apiquota.exception.ProducerEventException;
import com.vicarius.apiquota.util.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public abstract class ProducerEventImpl<T extends Object> implements ProducerEvent<T> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    public ProducerEventImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEvent(T eventDto) {
        String eventName = this.getEventName();
        log.info("Sending {}", eventName);
        String producerTopic = this.getTopic();
        try {
            this.kafkaTemplate.send(producerTopic,
                    objectMapper.writeValueAsString(eventDto)).addCallback((value) -> {
            }, new ProducerEventFallback(eventDto, producerTopic));
            log.info("producer-topic={}. Event={}", producerTopic, eventDto);
        } catch (JsonProcessingException e) {
            String message = String.format("Error processing producer-topic %s with message %s dto to event",
                    producerTopic, eventDto);
            ProducerEventException eventException = new ProducerEventException(message, e);
            LoggerNewRelic.logAndNotifyError(message, eventException, log);
        }
    }

    protected abstract String getTopic();

    protected abstract String getEventName();
}
