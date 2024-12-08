package com.example.service.question;

import com.example.dto.request.teacher.SearchQuestionsRequest;
import com.example.dto.response.teacher.SearchQuestionsResponse;

public interface SearchQuestionService {
    SearchQuestionsResponse searchQuestions(SearchQuestionsRequest request);
}
