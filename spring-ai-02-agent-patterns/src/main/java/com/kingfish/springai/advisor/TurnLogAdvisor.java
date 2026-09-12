package com.kingfish.springai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 挂在 ToolCallingAdvisor 环内，记录每一轮 Turn
 */
public class TurnLogAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(TurnLogAdvisor.class);

    /**
     * 本请求的 Turn 记录。
     */
    private final List<String> traces = new ArrayList<>();

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = chain.nextCall(request);
        if (response.chatResponse() == null || response.chatResponse().getResult() == null
                || response.chatResponse().getResult().getOutput() == null) {
            return response;
        }
        AssistantMessage output = response.chatResponse().getResult().getOutput();
        if (output.hasToolCalls()) {
            traces.add("Turn：模型要求调用 "
                    + output.getToolCalls().stream().map(AssistantMessage.ToolCall::name).collect(Collectors.joining("、")));
            log.info("[TurnLogAdvisor][toolCalls={}]", traces.get(traces.size() - 1));
            return response;
        }
        traces.add("Turn：模型给出文本回复");
        log.info("[TurnLogAdvisor][final text]");
        return response;
    }

    @Override
    public String getName() {
        return "TurnLog";
    }

    /**
     * 大于 ToolCallingAdvisor 默认 order，每轮 Turn 都会再跑一次。
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 400;
    }

    public List<String> getTraces() {
        return traces;
    }
}
