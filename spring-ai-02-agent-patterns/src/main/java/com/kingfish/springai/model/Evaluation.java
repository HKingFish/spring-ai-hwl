package com.kingfish.springai.model;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : Reflection 评审结果
 */
public class Evaluation {

    /**
     * 是否通过评审。
     */
    private boolean pass;

    /**
     * 未通过时的修改意见。
     */
    private String feedback;

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
