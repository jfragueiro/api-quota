package com.vicarius.apiquota.event.consumer;

import org.springframework.messaging.handler.annotation.Payload;

public interface ConsumerEvent {

    void consume(@Payload String message);
}
