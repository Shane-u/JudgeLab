package com.shane.judgeLab.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shane.judgeLab.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shane.judgeLab.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shane.judgeLab.model.entity.QuestionSubmit;
import com.shane.judgeLab.model.entity.User;
import com.shane.judgeLab.model.vo.QuestionSubmitVO;

/**
* @author Shane
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-01-30 17:50:15
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 提交记录的 id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

//    /**
//     * 题目提交（内部服务）
//     *
//     * @param userId
//     * @param questionId
//     * @return
//     */
//    int doQuestionSubmitInner(long userId, long questionId);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param userLogin
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User userLogin);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param userLogin
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User userLogin);
}
