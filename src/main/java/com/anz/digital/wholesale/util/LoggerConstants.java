package com.anz.digital.wholesale.util;


import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LoggerConstants {

    public enum LogKey {
        REQ_ID("reqId"),
        TRACE_ID("traceId"),
        PREXERROR("prexError"),
        ACCOUNT_NUMBER("account_number");
        private final String v;

        LogKey(String v) {
            this.v = v;
        }

        public static LogKey from(String text) {
            for (LogKey e : LogKey.values()) {
                if (e.v.equalsIgnoreCase(text)) {
                    return e;
                }
            }
            return null;
        }

        public String getValue() {
            return v;
        }
    }

    public enum AnzError {
        ERR_IO_TIMEOUT_Q_IBMMQ("ERR_IO_TIMEOUT_Q_IBMMQ"),
        ERR_IO_TIMEOUT_Q_SOLACE("ERR_IO_TIMEOUT_Q_SOLACE"),
        ERR_IO_TIMEOUT_DS_MONGO("ERR_IO_TIMEOUT_DS_MONGO"),
        ERR_IO_TIMEOUT_DS_S3("ERR_IO_TIMEOUT_DS_S3"),
        ERR_IO_CONNECT_Q_IBMMQ("ERR_IO_CONNECT_Q_IBMMQ"),
        ERR_IO_CONNECT_Q_SOLACE("ERR_IO_CONNECT_Q_SOLACE"),
        ERR_IO_CONNECT_DS_MONGO("ERR_IO_CONNECT_DS_MONGO"),
        ERR_IO_CONNECT_DS_S3("ERR_IO_CONNECT_DS_S3"),
        ERR_IO_DISCONNECT_Q_IBMMQ("ERR_IO_DISCONNECT_Q_IBMMQ"),
        ERR_IO_DISCONNECT_Q_SOLACE("ERR_IO_DISCONNECT_Q_SOLACE"),
        ERR_IO_DISCONNECT_DS_MONGO("ERR_IO_DISCONNECT_DS_MONGO"),
        ERR_IO_DISCONNECT_DS_S3("ERR_IO_DISCONNECT_DS_S3"),
        ERR_IO_WRITE_Q_IBMMQ("ERR_IO_WRITE_Q_IBMMQ"),
        ERR_IO_WRITE_Q_SOLACE("ERR_IO_WRITE_Q_SOLACE"),
        ERR_IO_WRITE_DS_MONGO("ERR_IO_WRITE_DS_MONGO"),
        ERR_IO_WRITE_DS_MONGO_RETRY("ERR_IO_WRITE_DS_MONGO_RETRY"),
        ERR_IO_WRITE_DS_S3("ERR_IO_WRITE_DS_S3"),
        ERR_IO_OTHER_Q_IBMMQ("ERR_IO_OTHER_Q_IBMMQ"),
        ERR_IO_OTHER_Q_SOLACE("ERR_IO_OTHER_Q_SOLACE"),
        ERR_IO_OTHER_DS_MONGO("ERR_IO_OTHER_DS_MONGO"),
        ERR_IO_OTHER_DS_S3("ERR_IO_OTHER_DS_S3"),
        ERR_UNMARSHAL_NONCONFORMAL("ERR_UNMARSHAL_NONCONFORMAL"),
        ERR_UNMARSHAL_UNHANDLEDSCHEMA("ERR_UNMARSHAL_UNHANDLEDSCHEMA"),
        ERR_UNMARSHAL_OTHER("ERR_UNMARSHAL_OTHER"),
        ERR_UNMARSHAL_UNHANDLEDFORMAT("ERR_UNMARSHAL_UNHANDLEDFORMAT"),
        ERR_MARSHAL("ERR_MARSHAL"),
        ERR("ERR"),
        ERR_REPLAY("ERR_REPLAY");

        private final String v;

        AnzError(String v) {
            this.v = v;
        }

        public static AnzError from(String text) {
            for (AnzError e : AnzError.values()) {
                if (e.v.equalsIgnoreCase(text)) {
                    return e;
                }
            }
            return null;
        }

        public String getValue() {
            return v;
        }
    }


    public enum AnzMarker {
        FLOW("FLOW"),
        EXCEPTION("EXCEPTION"),
        OTHER("OTHER");

        private final Marker v;

        AnzMarker(String v) {
            this.v = MarkerManager.getMarker(v);
        }

        public static AnzMarker from(String text) {
            for (AnzMarker e : AnzMarker.values()) {
                if (e.v.getName().equalsIgnoreCase(text)) {
                    return e;
                }
            }
            return null;
        }

        public Marker getValue() {
            return v;
        }
    }
}