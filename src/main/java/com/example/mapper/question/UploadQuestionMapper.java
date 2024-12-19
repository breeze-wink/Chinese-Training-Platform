package com.example.mapper.question;

import com.example.model.question.UploadQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UploadQuestionMapper {

    @Insert("INSERT INTO upload_question (teacherId, questionId, type) VALUES (#{teacherId}, #{questionId}, #{type})")
    void insert(UploadQuestion uploadQuestion);

    @Update("UPDATE upload_question SET teacherId = #{teacherId}, questionId = #{questionId}, type = #{type} WHERE id = #{id}")
    void update(UploadQuestion uploadQuestion);

    @Delete("DELETE FROM upload_question WHERE questionId = #{questionId} and type = #{type}")
    void delete(Long questionId, String type);

    @Select("SELECT * FROM upload_question WHERE id = #{id}")
    UploadQuestion findById(Long id);

    @Select("SELECT * FROM upload_question WHERE teacherId = #{teacherId}")
    List<UploadQuestion> findByTeacherId(Long teacherId);

    @Select("SELECT * FROM upload_question")
    List<UploadQuestion> findAll();

    @Select("SELECT teacherId FROM upload_question WHERE questionId = #{questionId} AND type = #{type}")
    Long findTeacherIdByQuestionIdAndType(Long questionId, String type);

    @Select("SELECT * FROM upload_question WHERE " +
            "teacherId IN (SELECT id from teacher WHERE schoolId = #{schoolId})")
    List<UploadQuestion> getInSchoolQuestions(Long schoolId);
}
