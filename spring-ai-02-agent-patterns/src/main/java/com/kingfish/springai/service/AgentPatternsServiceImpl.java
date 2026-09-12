package com.kingfish.springai.service;

import com.kingfish.springai.advisor.TurnLogAdvisor;
import com.kingfish.springai.model.Evaluation;
import com.kingfish.springai.model.PatternResult;
import com.kingfish.springai.model.Plan;
import com.kingfish.springai.tool.TravelTools;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 三种 Agent 模式的实现
 */
@Service
public class AgentPatternsServiceImpl implements AgentPatternsService {

    private static final Logger log = LoggerFactory.getLogger(AgentPatternsServiceImpl.class);

    /**
     * Reflection 最多评审轮数。
     */
    private static final int MAX_REFLECT_ROUND = 3;

    @Resource
    private ChatClient chatClient;

    @Resource
    private TravelTools travelTools;

    @Override
    public PatternResult react(String message) {
        log.info("[AgentPatternsService][react, query={}]", message);
        TurnLogAdvisor turnLogAdvisor = new TurnLogAdvisor();
        PatternResult result = new PatternResult();
        result.setPattern("react");
        result.setAnswer(chatClient.prompt()
                .system("你是旅行助手。需要天气、时间或景点信息时必须调用已提供的工具，再根据工具结果回答，不要编造。")
                .user(message)
                .tools(travelTools)
                .advisors(turnLogAdvisor)
                .call()
                .content());
        result.setTraces(turnLogAdvisor.getTraces());
        return result;
    }

    @Override
    public PatternResult planAndExecute(String goal) {
        log.info("[AgentPatternsService][planAndExecute, goal={}]", goal);
        PatternResult result = new PatternResult();
        result.setPattern("plan-execute");
        Plan plan = chatClient.prompt()
                .system("你是规划助手。只输出完成目标所需的步骤计划，不要执行。步骤应可独立执行，控制在 3 到 5 步。")
                .user(goal)
                .call()
                .entity(Plan.class);
        result.setPlan(plan);
        result.getTraces().add("规划完成，共 " + plan.getSteps().size() + " 步");

        StringBuilder context = new StringBuilder();
        for (int i = 0; i < plan.getSteps().size(); i++) {
            TurnLogAdvisor turnLogAdvisor = new TurnLogAdvisor();
            String stepAnswer = chatClient.prompt()
                    .system("你是执行助手。只完成当前这一步。需要天气、时间或景点时必须调用工具。")
                    .user("目标：" + goal
                            + "\n当前步骤：" + plan.getSteps().get(i).getName()
                            + "\n指令：" + plan.getSteps().get(i).getInstruction()
                            + "\n已有结果：\n" + context)
                    .tools(travelTools)
                    .advisors(turnLogAdvisor)
                    .call()
                    .content();
            result.getTraces().add("执行第 " + (i + 1) + " 步：" + plan.getSteps().get(i).getName());
            result.getTraces().addAll(turnLogAdvisor.getTraces());
            context.append("步骤 ").append(i + 1).append(" ").append(plan.getSteps().get(i).getName())
                    .append("：").append(stepAnswer).append("\n");
        }

        result.setAnswer(chatClient.prompt()
                .system("根据各步执行结果，给出对用户目标的最终回答，不要再规划。")
                .user("目标：" + goal + "\n各步结果：\n" + context)
                .call()
                .content());
        result.getTraces().add("汇总各步结果，给出最终回答");
        return result;
    }

    @Override
    public PatternResult reflect(String task) {
        log.info("[AgentPatternsService][reflect, task={}]", task);
        PatternResult result = new PatternResult();
        result.setPattern("reflection");
        String draft = chatClient.prompt()
                .system("按用户任务直接写出一稿，不要解释过程。")
                .user(task)
                .call()
                .content();
        result.getTraces().add("生成初稿");

        for (int round = 1; round <= MAX_REFLECT_ROUND; round++) {
            Evaluation evaluation = chatClient.prompt()
                    .system("你是评审。只判断稿件是否满足任务：约 80 字、有具体景点、少空话。"
                            + "通过则 pass=true；否则 pass=false，并给出可执行的修改意见。")
                    .user("任务：" + task + "\n稿件：\n" + draft)
                    .call()
                    .entity(Evaluation.class);
            result.getTraces().add("第 " + round + " 轮评审：" + (evaluation.isPass() ? "通过" : evaluation.getFeedback()));
            if (evaluation.isPass()) {
                break;
            }
            draft = chatClient.prompt()
                    .system("按评审意见改写，只输出改后正文。")
                    .user("任务：" + task + "\n原文：\n" + draft + "\n修改意见：\n" + evaluation.getFeedback())
                    .call()
                    .content();
            result.getTraces().add("按评审意见改写第 " + round + " 稿");
        }
        result.setAnswer(draft);
        return result;
    }
}
