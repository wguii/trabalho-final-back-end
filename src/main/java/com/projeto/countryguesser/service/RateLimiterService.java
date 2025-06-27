package com.projeto.countryguesser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    @Value("${rate.limit.perMinute}")
    private int limitPerMinute;

    @Value("${rate.limit.perHour}")
    private int limitPerHour;

    private Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String clientId) {
        RequestCounter counter = requestCounts.computeIfAbsent(clientId, k -> new RequestCounter());
        Instant now = Instant.now();

        // Reseta contadores se passou 1 minuto
        if (now.isAfter(counter.minuteReset)) {
            counter.minuteCount = 0;
            counter.minuteReset = now.plusSeconds(60);
        }

        // Reseta contadores se passou 1 hora
        if (now.isAfter(counter.hourReset)) {
            counter.hourCount = 0;
            counter.hourReset = now.plusSeconds(3600);
        }

        if (counter.minuteCount >= limitPerMinute || counter.hourCount >= limitPerHour) {
            return false;
        }

        counter.minuteCount++;
        counter.hourCount++;

        return true;
    }

    private static class RequestCounter {
        int minuteCount = 0;
        int hourCount = 0;
        Instant minuteReset = Instant.now().plusSeconds(60);
        Instant hourReset = Instant.now().plusSeconds(3600);
    }
}
