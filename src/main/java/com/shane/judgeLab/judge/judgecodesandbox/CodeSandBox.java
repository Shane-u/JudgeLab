package com.shane.judgeLab.judge.judgecodesandbox;


import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeRequest;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
