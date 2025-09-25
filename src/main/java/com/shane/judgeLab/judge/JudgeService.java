package com.shane.judgeLab.judge;


import com.shane.judgeLab.model.entity.QuestionSubmit;

public interface JudgeService {

    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

}
