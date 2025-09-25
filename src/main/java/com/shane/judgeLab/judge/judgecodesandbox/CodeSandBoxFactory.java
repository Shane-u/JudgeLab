package com.shane.judgeLab.judge.judgecodesandbox;


import com.shane.judgeLab.judge.judgecodesandbox.impl.ExampleCodeSandBoxImpl;
import com.shane.judgeLab.judge.judgecodesandbox.impl.RemoteCodeSandBoxImpl;
import com.shane.judgeLab.judge.judgecodesandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱工厂（根据指定的字符串参数来生成代码沙箱实例）
 * @author shane
 */
public class CodeSandBoxFactory {
    /**
     * 根据传入的参数生成对应的代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case "example":
                return new ExampleCodeSandBoxImpl();
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }

}
