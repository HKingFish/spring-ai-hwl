package com.kingfish.springai.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author : haowl
 * @Date : 2025/10/29 9:11
 * @Desc :
 */
@Service
public class BaseLlmServiceImpl implements BaseLlmService {

    private static final Logger log = LoggerFactory.getLogger(BaseLlmServiceImpl.class);

    @Resource
    private OpenAiChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    /**
     * MethodToolCallback 示例。
     */
    @Resource(name = "currentDateTimeToolCallback")
    private ToolCallback currentDateTimeToolCallback;

    /**
     * FunctionToolCallback 示例。
     */
    @Resource(name = "currentWeatherToolCallback")
    private ToolCallback currentWeatherToolCallback;

    @Override
    public String chat(String userMessage) {
        return chatModel.call(userMessage);
    }

    @Override
    public Flux<String> streamChat(String message) {
        return chatModel.stream(message);
    }

    @Override
    public String chatWithTools(String userMessage, String conversationId) {
        log.info("[BaseLlmService][chatWithTools, conversationId={}, query={}]", conversationId, userMessage);
        return chatClient.prompt()
                .system("你是助手，需要当前时间或天气时必须调用已提供的工具，再根据工具结果回答。")
                .user(userMessage)
                .tools(currentDateTimeToolCallback, currentWeatherToolCallback)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
