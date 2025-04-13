package com.learningseries.concurrency.ratelimiter.interceptors;

import com.learningseries.concurrency.ratelimiter.exceptions.RateLimitExceededException;
import com.learningseries.concurrency.ratelimiter.services.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing User ID");
            return false;
        }

        if (!rateLimiterService.isAllowed(userId)) {
            throw new RateLimitExceededException("Rate limit exceeded for user: " + userId);
        }

        return true;
    }

}
