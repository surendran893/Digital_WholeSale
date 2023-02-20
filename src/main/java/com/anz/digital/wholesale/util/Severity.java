package com.anz.digital.wholesale.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Severity {

    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    FATAL("FATAL");

    @Getter
    private String value;


}
