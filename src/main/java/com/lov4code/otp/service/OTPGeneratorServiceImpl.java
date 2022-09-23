package com.lov4code.otp.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPGeneratorServiceImpl implements OTPGeneratorService {
    private static final Integer EXPIRE_MIN = 5;
    private final LoadingCache<String, String> otpCache;

    public OTPGeneratorServiceImpl() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @NotNull
                    @Override
                    public String load(@NotNull String s) {
                        return "";
                    }
                });
    }

    @Override
    public String generateOTP(String key) {
        String cacheOTP = getOPTByKey(key);
        String strOtp;
        if (cacheOTP != null) {
            strOtp = cacheOTP;
        } else {
            Random rnd = new Random();
            int number = rnd.nextInt(9999);
            strOtp = String.format("%04d", number);
        }
        otpCache.put(key, strOtp);
        return strOtp;
    }

    @Override
    public String getOPTByKey(String key) {
        return otpCache.getIfPresent(key);
    }

    @Override
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }

    @Override
    public boolean validateOTP(String key, String otpNumber) {
        String cacheOTP = getOPTByKey(key);
        if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
            clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}
