package com.shane.judgeLab.judge.judgecodesandbox.impl;


import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBox;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeRequest;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeResponse;
import com.shane.judgeLab.judge.judgecodesandbox.model.JudgeInfo;
import com.shane.judgeLab.model.enums.JudgeInfoMessageEnum;
import com.shane.judgeLab.model.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 示例代码沙箱(仅仅为了跑通业务流程)
 */
@Slf4j
public class ExampleCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();

        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage(QuestionSubmitStatusEnum.SUCCEED.getText());
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(1000L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
