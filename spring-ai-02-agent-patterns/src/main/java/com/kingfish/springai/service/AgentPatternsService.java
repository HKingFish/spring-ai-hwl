package com.kingfish.springai.service;

import com.kingfish.springai.model.PatternResult;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 三种 Agent 模式
 */
public interface AgentPatternsService {

    /**
     * ReAct：模型边想边调工具。
     *
     * @param message 用户问题
     * @return 执行结果
     */
    PatternResult react(String message);

    /**
     * Plan & Execute：先规划再逐步执行。
     *
     * @param goal 用户目标
     * @return 执行结果
     */
    PatternResult planAndExecute(String goal);

    /**
     * Reflection：生成后再评审、改写。
     *
     * @param task 写作任务
     * @return 执行结果
     */
    PatternResult reflect(String task);
}
