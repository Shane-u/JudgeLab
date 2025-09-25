package com.shane.judgeLab.judge.judgecodesandbox.model;


import lombok.Data;

/**
 * 判题信息
 */

@Data // lombok 自动生成getter、setter、equals、canEqual、hashCode、toString方法
public class JudgeInfo {

    /**
     * 消耗时间(ms)
     */
    private Long time;

    /**
     * 消耗内存(kb)
     */
    private Long memory;

    /**
     * 程序执行信息
     */
    private String message;
}
