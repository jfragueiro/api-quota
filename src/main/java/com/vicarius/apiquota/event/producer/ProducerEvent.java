package com.vicarius.apiquota.event.producer;

public interface ProducerEvent<T extends Object> {

    void sendEvent(T eventDto);
}
