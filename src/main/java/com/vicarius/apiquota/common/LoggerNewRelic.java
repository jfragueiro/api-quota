package com.vicarius.apiquota.common;

import com.vicarius.apiquota.exception.TemplateException;
import com.google.common.collect.Maps;
import com.newrelic.api.agent.NewRelic;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;

import java.util.Map;

public class LoggerNewRelic {

    public static void logAndNotifyError(String errorMsg, Throwable e, Logger logger) {
        logger.error(errorMsg, e);
        notifyError(errorMsg, null, e, false);
    }

    public static void logAndNotifyError(String logPlace, String errorMsg, Throwable e, Logger logger) {
        logger.error(logPlace, errorMsg, e);
        notifyError(errorMsg, null, e, false);
    }

    public static void logAndNotifyError(String errorMsg, Throwable e, Logger logger, Map<String, String> params) {
        logger.error(errorMsg, e);
        notifyError(errorMsg, params, e, false);
    }

    private static void notifyError(String errorMsg, Map<String, String> params, Throwable e, boolean expected) {
        Map<String, String> mapError = Maps.newHashMap();
        if (MapUtils.isNotEmpty(params)) {
            mapError.putAll(params);
        }
        mapError.put("ERROR", errorMsg);
        mapError.put("error_message", errorMsg);
        NewRelic.noticeError(e, mapError, expected);
    }

    public static void logAndThrowOnboardingExceptionException(String message, Logger log) {
        TemplateException exception = new TemplateException(message);
        LoggerNewRelic.logAndNotifyError(message, exception, log);
        throw exception;
    }
}
