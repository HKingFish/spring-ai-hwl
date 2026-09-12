package com.kingfish.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : Agent 示例的 ChatClient 装配
 */
@Configuration
public class AgentPatternsConfig {

    /**
     * 不预置记忆和工具，三种模式在调用时自行拼装。
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel).build();
    }
}
