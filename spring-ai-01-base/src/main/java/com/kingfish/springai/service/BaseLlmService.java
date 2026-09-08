package com.kingfish.springai.service;

import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * @Author : haowl
 * @Date : 2025/10/29 9:11
 * @Desc :
 */
public interface BaseLlmService {


    /**
     * 简单的问答
     *
     * @param userMessage 用户输入的消息
     * @return 模型返回的消息
     */
    String chat(String userMessage);

    /**
     * 流式问答
     *
     * @param message 用户输入的消息
     * @return 模型返回的消息流
     */
    Flux<String> streamChat(String message);



}
