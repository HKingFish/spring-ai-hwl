package com.kingfish.springai.service;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.*;


/**
 * @Author : haowl
 * @Date : 2025/10/29 9:11
 * @Desc :
 */
@Service
public class BaseLlmServiceImpl implements BaseLlmService {

    @Resource
    private OpenAiChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    @Override
    public String chat(String userMessage) {
        return chatModel.call(userMessage);
    }

    @Override
    public Flux<String> streamChat(String message) {
        return chatModel.stream(message);
    }


}
