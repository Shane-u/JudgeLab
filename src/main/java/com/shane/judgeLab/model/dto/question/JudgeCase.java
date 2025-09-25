package com.shane.judgeLab.model.dto.question;


import lombok.Data;

/**
 * 题目判题用例
 */

@Data // lombok 自动生成getter、setter、equals、canEqual、hashCode、toString方法
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
