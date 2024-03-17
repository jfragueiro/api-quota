package com.vicarius.apiquota.exception;

public class ProducerEventException extends TemplateException {

    public ProducerEventException(String message) {
        super(message);
    }

    public ProducerEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
