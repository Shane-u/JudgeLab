package com.shane.judgeLab.judge.judgecodesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.shane.judgeLab.common.ErrorCode;
import com.shane.judgeLab.exception.BusinessException;
import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBox;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeRequest;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;


/**
 * 远程代码沙箱
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {

    // 鉴权请求头和密钥
    private final String AUTH_REQUEST_HEADER = "auth";

    private final String AUTH_REQUEST_SECURET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8080/executeCode";
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);
        String response = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECURET)
                .body(jsonStr)
                .execute()
                .body();
        if(StringUtils.isBlank(response)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandBox error, message = " + response);
        }
        return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
