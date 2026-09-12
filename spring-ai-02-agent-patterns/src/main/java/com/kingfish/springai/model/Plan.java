package com.kingfish.springai.model;

import java.util.List;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : Plan & Execute 的规划结果
 */
public class Plan {

    /**
     * 用户目标。
     */
    private String goal;

    /**
     * 拆好的执行步骤。
     */
    private List<PlanStep> steps;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public List<PlanStep> getSteps() {
        return steps;
    }

    public void setSteps(List<PlanStep> steps) {
        this.steps = steps;
    }
}
