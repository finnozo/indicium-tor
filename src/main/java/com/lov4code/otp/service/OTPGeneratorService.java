package com.lov4code.otp.service;

public interface OTPGeneratorService {
    String generateOTP(String key);
    String getOPTByKey(String key);
    void clearOTPFromCache(String key);

    boolean validateOTP(String key, String otpNumber);
}
