package com.vicarius.apiquota.event.producer.impl;

import com.vicarius.apiquota.common.LoggerNewRelic;
import com.vicarius.apiquota.exception.ProducerEventException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.FailureCallback;

@Slf4j
public class ProducerEventFallback implements FailureCallback {

    private final Object messageValue;
    private final String messageKey;

    public ProducerEventFallback(Object messageValue, String messageKey) {
        this.messageValue = messageValue;
        this.messageKey = messageKey;
    }

    @Override
    public void onFailure(Throwable throwable) {
        String message = String.format("Error sending event %s %s", this.messageKey, this.messageValue);
        ProducerEventException eventException = new ProducerEventException(message, throwable);
        LoggerNewRelic.logAndNotifyError(message, eventException, log);
    }
}
