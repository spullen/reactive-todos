package net.scottpullen;

public enum Environment {
    PRODUCTION,
    DEVELOPMENT,
    TEST;

    public static final Environment DEFAULT = DEVELOPMENT;
}