package com.learningseries.concurrency.ratelimiter.services;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private final int MAX_REQUESTS = 5;
    private final long TIME_WINDOW_MS = 60 * 1000; // 1 minute

    private final Map<String, Deque<Long>> userRequestMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();
        userRequestMap.putIfAbsent(userId, new ArrayDeque<>());

        Deque<Long> timestamps = userRequestMap.get(userId);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() > TIME_WINDOW_MS) {
                timestamps.pollFirst(); // Remove outdated entries
            }

            if (timestamps.size() < MAX_REQUESTS) {
                timestamps.offerLast(now);
                return true;
            } else {
                return false;
            }
        }
    }
}
