package net.scottpullen.common;

import org.apache.commons.lang3.StringUtils;

public class ArgumentPreconditions {

    public static void required(Object o, String failureMessage) {
        if(o == null) {
            throw new IllegalArgumentException(failureMessage);
        }
    }

    public static void notBlank(String o, String failureMessage) {
        if(StringUtils.isBlank(o)) {
            throw new IllegalArgumentException(failureMessage);
        }
    }
}
