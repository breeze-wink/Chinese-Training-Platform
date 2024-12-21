package com.example.service.question.impl;

import com.example.dto.request.teacher.SearchQuestionsRequest;
import com.example.dto.response.teacher.SearchQuestionsResponse;
import com.example.dto.mapper.BigQuestionResult;
import com.example.dto.mapper.QuestionResult;
import com.example.mapper.question.SearchQuestionMapper;
import com.example.model.question.Question;
import com.example.service.course.KnowledgePointService;
import com.example.service.question.QuestionService;
import com.example.service.question.SearchQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchQuestionServiceImpl implements SearchQuestionService {

    @Autowired
    private SearchQuestionMapper searchQuestionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private KnowledgePointService knowledgePointService;

    @Override
    public SearchQuestionsResponse searchQuestions(SearchQuestionsRequest request) {
        SearchQuestionsResponse response = new SearchQuestionsResponse();

        // 初始化 lists，防止空指针异常
        response.setBigQuestions(new ArrayList<>());
        response.setQuestions(new ArrayList<>());

        // 计算偏移量
        int offset = (request.getPage() - 1) * request.getPageSize();

        // 校验 sortOrder
        if (!"asc".equalsIgnoreCase(request.getSortOrder()) &&
                !"desc".equalsIgnoreCase(request.getSortOrder())) {
            request.setSortOrder("desc"); // 设置默认值
        }

        // 判断查询类型：大题或小题

        boolean isBigQuestion = request.getKnowledgeId() == null;

        if (isBigQuestion) {
            // 查询大题
            try {
                List<BigQuestionResult> bigQuestionResults = searchQuestionMapper.searchBigQuestions(
                        request.getKnowledgeType(),
                        request.getDifficulty(),
                        request.getMode(),
                        request.getSortOrder(),
                        offset,
                        request.getPageSize(),
                        request.getSearch()
                );
                // 设置大题数据
                for (BigQuestionResult result : bigQuestionResults) {
                    SearchQuestionsResponse.BigQuestion bigQuestion = new SearchQuestionsResponse.BigQuestion();
                    bigQuestion.setBodyId(result.getBodyId());
                    bigQuestion.setBody(result.getBody());
                    if (result.getCompleteCount() == 0) {
                        bigQuestion.setDifficulty(null);
                    }
                    else {
                        bigQuestion.setDifficulty(result.getTotalScore() / result.getCompleteCount());
                    }

                    bigQuestion.setReferencedCount(result.getReferencedCount());
                    bigQuestion.setReferencedCount(result.getReferencedCount());
                    // 查询子题
                    List<Question> subQuestions = questionService.getQuestionsByQuestionBodyId(result.getBodyId());
                    List<SearchQuestionsResponse.SubQuestion> subQuestionDTOs = convertToSubQuestions(subQuestions);
                    bigQuestion.setSubQuestion(subQuestionDTOs);
                    response.getBigQuestions().add(bigQuestion);
                }

                // 获取总记录数
                int totalCount = searchQuestionMapper.countBigQuestions(
                        request.getKnowledgeType(),
                        request.getDifficulty(),
                        request.getSearch()
                );
                response.setPageSize(request.getPageSize() == bigQuestionResults.size() ?
                        request.getPageSize() : bigQuestionResults.size());

                response.setTotalCount(totalCount);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            // 查询小题
            List<QuestionResult> questionResults = searchQuestionMapper.searchSmallQuestions(
                    request.getType(),
                    request.getKnowledgeId(),
                    request.getDifficulty(),
                    request.getMode(),
                    request.getSortOrder(),
                    offset,
                    request.getPageSize(),
                    request.getSearch()
            );

            // 设置小题数据
            for (QuestionResult result : questionResults) {
                SearchQuestionsResponse.Question question = new SearchQuestionsResponse.Question();
                question.setQuestionId(result.getQuestionId());
                question.setBody(result.getBody());
                question.setQuestion(result.getContent());
                String answers = result.getAnswer();
                String[] temps = answers.split("\\$\\$");

                if (result.getType().equals("FILL_IN_BLANK")) {
                    temps[0] = temps[0].replaceAll("##", ";");
                }
                question.setAnswer(temps[0]);

                if (temps.length > 1) {
                    question.setExplanation(temps[1]); // 需要根据实际数据填充
                }
                if (result.getCompleteCount() == 0) {
                    question.setDifficulty(null);
                }
                else {
                    question.setDifficulty(result.getTotalScore() / result.getCompleteCount());
                }
                question.setType(result.getType());
                if (result.getOptions() != null) {
                    question.setOptions(Arrays.stream(result.getOptions().split("\\$\\$")).toList());
                }
                question.setReferencedCount(result.getReferencedCount());
                question.setKnowledge(result.getKnowledgeName()); // 设置实际知识点名称
                response.getQuestions().add(question);
            }

            // 获取总记录数
            long totalCount = searchQuestionMapper.countSmallQuestions(
                    request.getType(),
                    request.getKnowledgeId(),
                    request.getDifficulty(),
                    request.getSearch()
            );
            response.setPageSize(questionResults.size() == request.getPageSize() ?
                                 request.getPageSize() : questionResults.size());
            response.setTotalCount((int) totalCount);
        }

        // 设置分页信息
        response.setCurrentPage(request.getPage());
        int totalPages = (int) Math.ceil((double) response.getTotalCount() / request.getPageSize());
        response.setTotalPages(totalPages);

        return response;
    }

    private List<SearchQuestionsResponse.SubQuestion> convertToSubQuestions(List<Question> questions) {
        return questions.stream().map(q -> {
            SearchQuestionsResponse.SubQuestion sub = new SearchQuestionsResponse.SubQuestion();
            sub.setQuestion(q.getContent());
            String answer = q.getAnswer();

            String[] temps = answer.split("\\$\\$");
            if (q.getType().equals("FILL_IN_BLANK")) {
                temps[0] = temps[0].replaceAll("##", ";");
            }
            sub.setAnswer(temps[0]);
            if (temps.length > 1) {
                sub.setExplanation(temps[1]); // 需要根据实际数据填充
            }
            sub.setType(q.getType());
            if (q.getOptions() != null) {
                sub.setOptions(Arrays.stream(q.getOptions().split("\\$\\$")).toList());
            }
            // 获取知识点名称
            String knowledgeName = fetchKnowledgeName(q.getKnowledgePointId());
            sub.setKnowledge(knowledgeName);
            return sub;
        }).toList();
    }

    private String fetchKnowledgeName(Long knowledgePointId) {
        // 调用 KnowledgePointService 获取知识点名称
        // 假设 KnowledgePointService 已经注入
        // 这里为了简化，返回空字符串，实际应调用 service 获取
        return knowledgePointService.getKnowledgePointNameById(knowledgePointId);
    }

    private String determineDifficulty(Double totalScore, Integer completeCount) {
        if (completeCount == null || completeCount == 0) {
            return "容易";
        }
        double rate = totalScore / completeCount;
        if (rate >= 0.8) {
            return "容易";
        } else if (rate >= 0.5) {
            return "普通";
        } else {
            return "困难";
        }
    }



}
