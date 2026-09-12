package com.kingfish.springai.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 三种模式的统一返回，方便对照执行过程
 */
public class PatternResult {

    /**
     * 模式名：react / plan-execute / reflection。
     */
    private String pattern;

    /**
     * 最终回答。
     */
    private String answer;

    /**
     * 规划结果，仅 Plan & Execute 有值。
     */
    private Plan plan;

    /**
     * 每一步过程，便于博客对照。
     */
    private List<String> traces = new ArrayList<>();

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<String> getTraces() {
        return traces;
    }

    public void setTraces(List<String> traces) {
        this.traces = traces;
    }
}
