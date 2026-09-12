package com.kingfish.springai.model;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 计划中的一步
 */
public class PlanStep {

    /**
     * 步骤名称。
     */
    private String name;

    /**
     * 这一步要完成的指令。
     */
    private String instruction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
