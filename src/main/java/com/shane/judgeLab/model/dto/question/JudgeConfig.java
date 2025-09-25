package com.shane.judgeLab.model.dto.question;


import lombok.Data;

/**
 * 判断配置用例
 */

@Data // lombok 自动生成getter、setter、equals、canEqual、hashCode、toString方法
public class JudgeConfig {

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制(kb)
     */
    private Long stackLimit;
}
