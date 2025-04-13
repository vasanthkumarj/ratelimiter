package com.learningseries.concurrency.ratelimiter.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimitedController {

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestParam String userId) {
        return ResponseEntity.ok("Here is your data 🚀");
    }

}
