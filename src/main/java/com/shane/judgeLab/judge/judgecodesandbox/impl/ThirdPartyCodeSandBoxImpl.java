package com.shane.judgeLab.judge.judgecodesandbox.impl;


import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBox;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeRequest;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
