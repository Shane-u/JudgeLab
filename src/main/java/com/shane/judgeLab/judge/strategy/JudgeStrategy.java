package com.shane.judgeLab.judge.strategy;


import com.shane.judgeLab.judge.judgecodesandbox.model.JudgeInfo;

public interface JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
