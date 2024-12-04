package com.example;

import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private Question question;

//    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setContent("What is Java?");
        question.setType("CHOICE");
        question.setOptions("[\"Option A\", \"Option B\", \"Option C\"]");
        question.setAnswer("Option B");
        question.setKnowledgePointId(18L);
        question.setCreatorId(316L);
        question.setBodyId(343L);
    }

    @Test
    void testCreateQuestion() {
        question = new Question();
        question.setId(1L);
        question.setContent("What is Java?");
        question.setType("CHOICE");
        question.setOptions("[\"Option A\", \"Option B\", \"Option C\"]");
        question.setAnswer("Option B");
        question.setKnowledgePointId(18L);
        question.setCreatorId(316L);
        question.setBodyId(343L);
        int result = questionService.createQuestion(question);
        assertTrue(result > 0);
    }

    @Test
    void testDeleteQuestion() throws Exception {
//        questionService.createQuestion(question); // 确保存在可以删除的记录
        int result = questionService.deleteQuestion(291L);
        assertTrue(result > 0);
    }

    @Test
    void testGetQuestionById() throws Exception {
//        questionService.createQuestion(question); // 确保存在可以获取的记录
        Question retrievedQuestion = questionService.getQuestionById(287L);
        assertNotNull(retrievedQuestion);
    }

    @Test
    void testGetQuestionsByKnowledgePointId() {
        List<Question> questions = questionService.getQuestionsByKnowledgePointId(18L);
        assertFalse(questions.isEmpty());
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        assertFalse(questions.isEmpty());
    }

    @Test
    void testGetQuestionsByQuestionBodyId() {
        List<Question> questions = questionService.getQuestionsByQuestionBodyId(343L);
        assertFalse(questions.isEmpty());
    }


}
   