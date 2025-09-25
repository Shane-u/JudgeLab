package com.shane.judgeLab.judge.judgecodesandbox.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 一组输入用例
     */
    private List<String> inputList;

    /**
     * 程序代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;
}
