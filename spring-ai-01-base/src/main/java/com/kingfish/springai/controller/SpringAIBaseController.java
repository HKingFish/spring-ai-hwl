package com.kingfish.springai.controller;

import com.kingfish.springai.service.BaseLlmService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author : haowl
 * @Date : 2026/6/6 14:23
 * @Desc :
 */
@RestController
@RequestMapping("/base")
public class SpringAIBaseController {


    @Resource
    private BaseLlmService baseLlmService;

    /**
     * 基础聊天
     *
     * @param message
     * @return
     */
    @PostMapping("chat")
    public String chat(String message) {
        return baseLlmService.chat(message);
    }

    /**
     * 流式聊天
     *
     * @param message
     * @return
     */
    @PostMapping(value = "streamChat", produces = "text/html;charset=utf-8")
    public Flux<String> streamChat(String message) {
        return baseLlmService.streamChat(message);
    }


}
