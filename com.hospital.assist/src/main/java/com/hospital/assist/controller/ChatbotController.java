package com.hospital.assist.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @PostMapping("/ask")
    public Map<String, String> ask(@RequestBody Map<String, String> body) {
        String q = body.getOrDefault("q", "").toLowerCase();

        if (q.contains("visiting"))
            return Map.of("answer", "Visiting hours are 9:00 AM to 7:00 PM daily.");
        if (q.contains("emergency"))
            return Map.of("answer", "For emergencies, go to the emergency department or call the hospital helpline.");
        if (q.contains("appointment"))
            return Map.of("answer", "You can book, reschedule, or cancel appointments from your Patient page.");
        return Map.of("answer", "I didn't quite get that. For appointments, use the Patient page.");
    }
}
