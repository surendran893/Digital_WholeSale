package com.anz.digital.wholesale.util;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class AnzLogger {

    private static int maxStackTraceSize;

    static {
        Properties properties = new Properties();
        try (InputStream s =
                     AnzLogger.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(s);
            maxStackTraceSize = Integer.parseInt(properties.getProperty("logging.maxStackTraceSize"));
        } catch (Exception t) {
            maxStackTraceSize = 999999;
        }
    }

    private final Logger log;

    private AnzLogger(final Logger log) {
        this.log = log;
    }

    public static AnzLogger getLogger(Class<?> clazz) {
        return new AnzLogger(LogManager.getLogger(clazz.getName()));
    }

    public void trace(LoggerConstants.AnzMarker marker, String message, Object... params) {
        log.trace(marker.getValue(), message, params);
    }

    public void trace(LoggerConstants.AnzMarker marker, String message) {
        log.trace(marker.getValue(), message);
    }

    public void debug(LoggerConstants.AnzMarker marker, String message, Object... params) {
        log.debug(marker.getValue(), message, params);
    }

    public void debug(LoggerConstants.AnzMarker marker, String message) {
        log.debug(marker.getValue(), message);
    }

    public void info(
            LoggerConstants.AnzMarker marker, Map<String, String> localMap, String message) {
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(localMap)) {
            log.info(marker.getValue(), message);
        }
    }

    public void info(LoggerConstants.AnzMarker marker, String message, Object... params) {
        log.info(marker.getValue(), message, params);
    }

    public void info(LoggerConstants.AnzMarker marker, Map<String, String> localMap, String message, Object... params) {
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(localMap)) {
            log.info(marker.getValue(), message, params);
        }
    }

    public void info(LoggerConstants.AnzMarker marker, String message) {
        log.info(marker.getValue(), message);
    }

    public void warn(LoggerConstants.AnzMarker marker, String message, Object... params) {
        log.warn(marker.getValue(), message, params);
    }

    public void warn(LoggerConstants.AnzMarker marker, String message) {
        log.warn(marker.getValue(), message);
    }

    public void error(LoggerConstants.AnzError error, String message, Object... params) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())) {
            log.error(LoggerConstants.AnzMarker.EXCEPTION.getValue(), message, params);
        }
    }

    public void error(LoggerConstants.AnzError error, String message) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())) {
            log.error(LoggerConstants.AnzMarker.EXCEPTION.getValue(), message);
        }
    }

    public void catching(LoggerConstants.AnzError error, Throwable t, Map<String, String> localMap) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())
                             .putAll(localMap)) {
            if (t.getStackTrace().length > maxStackTraceSize) {
                t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
            }
            log.catching(t);
        }
    }

    public void catching(LoggerConstants.AnzError error, Throwable t) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())) {
            if (t.getStackTrace().length > maxStackTraceSize) {
                t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
            }
            log.catching(t);
        }
    }

    public void catching(Throwable t) {
        if (t.getStackTrace().length > maxStackTraceSize) {
            t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
        }
        log.catching(t);
    }

    public void throwing(LoggerConstants.AnzError error, Throwable t, Map<String, String> localMap) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())
                             .putAll(localMap)) {
            if (t.getStackTrace().length > maxStackTraceSize) {
                t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
            }
            log.throwing(t);
        }
    }

    public void throwing(LoggerConstants.AnzError error, Throwable t) {
        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(LoggerConstants.LogKey.PREXERROR.getValue(), error.name())) {
            if (t.getStackTrace().length > maxStackTraceSize) {
                t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
            }
            log.throwing(t);
        }
    }

    public <T extends Throwable> T throwing(T t) {
        if (t.getStackTrace().length > maxStackTraceSize) {
            t.setStackTrace(Arrays.copyOf(t.getStackTrace(), maxStackTraceSize));
        }
        return log.throwing(t);
    }
}