package com.lov4code.util;

public interface AppConstants {
    String DATE_FORMATTING_STRING = "dd MMM yyyy";
    String DATE_TIME_FORMATTING_STRING = "dd MMM yyyy hh:mm:ss";
    String TIME_FORMATTING_STRING = "hh:mm";
    Long TOKEN_EXPIRY = 1000L * 60 * 60 * 24;

    String PRIVATE_KEY_PATH = "/springsecurity.jks";
    String ISSUER_ALIAS = "springsecurity";
    char[] KEY_PASSWORD = "Hello@123".toCharArray();
    String INSTANCE_TYPE="JKS";
}
