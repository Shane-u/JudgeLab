package com.shane.judgeLab.judge;

import com.shane.judgeLab.judge.judgecodesandbox.model.JudgeInfo;
import com.shane.judgeLab.judge.strategy.DefaultJudgeStrategy;
import com.shane.judgeLab.judge.strategy.JavaJudgeStrategy;
import com.shane.judgeLab.judge.strategy.JudgeContext;
import com.shane.judgeLab.judge.strategy.JudgeStrategy;
import com.shane.judgeLab.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理，简化调用
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        // 根据语言动态获取对应的判题策略
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if(language.equals(QuestionSubmitLanguageEnum.JAVA.getValue())){
            judgeStrategy = new JavaJudgeStrategy();
        }
        // 把上下文再传递回去
        return judgeStrategy.doJudge(judgeContext);
    }

}
