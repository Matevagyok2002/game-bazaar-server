package com.gamebazaar.gamebazaarserver.security;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenManager {
    private static Map<String, Long> tokens = new HashMap<>();
    private static final long EXPIRATION_HOURS = 12;
    private static final long CLEANUP_PERIOD_HOURS = 12;

    public TokenManager() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> cleanUpExpiredTokens(), 0, CLEANUP_PERIOD_HOURS, TimeUnit.HOURS);
    }

    public void addToken(String token) {
        long expiration = System.currentTimeMillis()+(60*60*1000*EXPIRATION_HOURS);
        tokens.put(token, expiration);
    }

    public void deleteToken(String token) {
        tokens.remove(token);
    }

    private void cleanUpExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> token : tokens.entrySet()){
            if (currentTime >= token.getValue()) {
                tokens.remove(token.getKey());
            }
        }
    }

    public Map<String, Long> getTokens() {
        return tokens;
    }
}