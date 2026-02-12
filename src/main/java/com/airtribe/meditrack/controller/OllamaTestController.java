package com.airtribe.meditrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaTestController {

    private final ChatClient ollamaChatClient;

    // We use the constructor to inject the specific "ollamaChatClient" bean
    public OllamaTestController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/test-ollama")
    public String testOllama(@RequestParam(defaultValue = "Hello, who are you?") String message) {
        // Use the pre-injected client directly
        return ollamaChatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}