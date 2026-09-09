package com.kingfish.springai.config;

import com.kingfish.springai.tool.DemoTools;
import com.kingfish.springai.tool.WeatherQuery;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinitions;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Author : haowl
 * @Date : 2026/6/6 14:09
 * @Desc : ChatMemory 与 ChatClient 装配，模型由 spring.ai.openai.* 自动配置
 */
@Configuration
public class SpringAIBaseConfig {

    /**
     * 对话记忆：滑动窗口，保留最近10轮消息(可修改)，内存存储
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();
    }

    /**
     * ChatModel 的高级封装
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("你是专业的怼人高手")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    /**
     * 把已有方法包装成 Tool。
     */
    @Bean
    public ToolCallback currentDateTimeToolCallback() {
        Method method = ReflectionUtils.findMethod(DemoTools.class, "getCurrentDateTime");
        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinitions.builder(method)
                        .description("获取当前日期和时间")
                        .build())
                .toolMethod(method)
                .toolObject(new DemoTools())
                .build();
    }

    /**
     * 把 Function 包装成 Tool。
     */
    @Bean
    public ToolCallback currentWeatherToolCallback() {
        return FunctionToolCallback.builder("getCurrentWeather", (WeatherQuery query) -> new DemoTools().getWeather(query))
                .description("查询指定城市的当前天气")
                .inputType(WeatherQuery.class)
                .build();
    }
}
