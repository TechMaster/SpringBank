package com.xbank.util;

import java.util.UUID;

public final class UuidUtil {

    private UuidUtil() {
        throw new UnsupportedOperationException("Cannot instantiate a Util class");
    }

    public static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
