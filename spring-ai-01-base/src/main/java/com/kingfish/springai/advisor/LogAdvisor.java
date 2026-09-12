package com.kingfish.springai.advisor;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;

/**
 * @author : haowl
 * @date : 2026/9/10 19:43
 * @desc : 日志打印 Advisor
 */
@Slf4j
public class LogAdvisor implements BaseAdvisor {
    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        log.info("before: {}", JSON.toJSONString(chatClientRequest));
        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        log.info("after: {}", JSON.toJSONString(chatClientResponse));
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}