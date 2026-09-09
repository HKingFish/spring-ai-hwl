package com.kingfish.springai.controller;

import com.kingfish.springai.service.BaseLlmService;
import jakarta.annotation.Resource;
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

    /**
     * 未传会话 ID 时的默认会话。
     */
    private static final String DEFAULT_CONVERSATION_ID = "tool-demo";

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

    /**
     * 带 Tool 的聊天
     *
     * @param message 用户输入
     * @param conversationId 会话 ID，可空
     * @return 模型回复
     */
    @PostMapping("toolChat")
    public String toolChat(String message, String conversationId) {
        return baseLlmService.chatWithTools(message,
                conversationId == null || conversationId.isBlank() ? DEFAULT_CONVERSATION_ID : conversationId);
    }

}
