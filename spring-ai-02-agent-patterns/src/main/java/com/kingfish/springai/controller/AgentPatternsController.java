package com.kingfish.springai.controller;

import com.kingfish.springai.model.PatternResult;
import com.kingfish.springai.service.AgentPatternsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 三种 Agent 模式的 HTTP 入口
 */
@RestController
@RequestMapping("/pattern")
public class AgentPatternsController {

    @Resource
    private AgentPatternsService agentPatternsService;

    /**
     * ReAct：模型自己决定调哪些工具。
     *
     * @param message 例如：帮我查一下杭州今天天气，再告诉我现在几点
     * @return 执行结果
     */
    @PostMapping("react")
    public PatternResult react(String message) {
        return agentPatternsService.react(message);
    }

    /**
     * Plan & Execute：先出计划再逐步执行。
     *
     * @param message 例如：规划一次杭州半日游，先看天气再推荐景点
     * @return 执行结果
     */
    @PostMapping("planExecute")
    public PatternResult planAndExecute(String message) {
        return agentPatternsService.planAndExecute(message);
    }

    /**
     * Reflection：写一稿，评审不过就改。
     *
     * @param message 例如：写一段约 80 字的杭州旅游介绍，要点出具体景点
     * @return 执行结果
     */
    @PostMapping("reflect")
    public PatternResult reflect(String message) {
        return agentPatternsService.reflect(message);
    }
}
