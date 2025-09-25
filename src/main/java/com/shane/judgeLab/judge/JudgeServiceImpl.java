package com.shane.judgeLab.judge;


import cn.hutool.json.JSONUtil;
import com.shane.judgeLab.common.ErrorCode;
import com.shane.judgeLab.exception.BusinessException;
import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBox;
import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBoxFactory;
import com.shane.judgeLab.judge.judgecodesandbox.CodeSandBoxProxy;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeRequest;
import com.shane.judgeLab.judge.judgecodesandbox.model.ExecuteCodeResponse;
import com.shane.judgeLab.judge.judgecodesandbox.model.JudgeInfo;
import com.shane.judgeLab.judge.strategy.JudgeContext;
import com.shane.judgeLab.model.dto.question.JudgeCase;
import com.shane.judgeLab.model.entity.Question;
import com.shane.judgeLab.model.entity.QuestionSubmit;
import com.shane.judgeLab.model.enums.QuestionSubmitStatusEnum;
import com.shane.judgeLab.service.QuestionService;
import com.shane.judgeLab.service.QuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    /**
     * 题目服务
     */
    @Resource
    private QuestionService questionService;

    /**
     * 题目提交服务
     */
    @Resource
    private QuestionSubmitService questionSubmitService;


    @Resource
    private JudgeManager judgeManager;

    /**
     * 获取动态配置
     */
    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1）传入题目的提交id，获取到对应的题目、提交信息（包含代码，编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2）如果题目状态为待判题，那么才执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //3）更新题目的提交状态为 “判题中”
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(updateQuestionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        //4）调用沙箱，获取判题结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);

        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        // 流操作
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        //5）根据沙箱的执行结果，设置题目的判题状态和信息。
        // 封装上下文
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

//        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
//        if(questionSubmit.getLanguage().equals(QuestionSubmitLanguageEnum.JAVA.getValue())){
//            judgeStrategy = new JavaJudgeStrategy();
//        }
        // 优化

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 在数据库更新提交信息
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(updateQuestionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        // 从数据库中查询并返回
        return questionSubmitService.getById(questionSubmitId);
    }
}
