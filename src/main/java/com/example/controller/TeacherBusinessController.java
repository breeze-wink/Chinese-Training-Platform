package com.example.controller;

import com.example.dto.request.TeacherCreateClassRequest;
import com.example.dto.response.TeacherCreateClassResponse;
import com.example.model.course.CourseStandard;
import com.example.service.classes.ClassService;
import com.example.service.classes.impl.ClassServiceImpl;
import com.example.service.course.CourseStandardService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/teacher")
public class TeacherBusinessController {
    private final CourseStandardService courseStandardService;
    private final ClassService classService;
    @Autowired
    public TeacherBusinessController(CourseStandardServiceImpl courseStandardService, ClassServiceImpl classService) {
        this.courseStandardService = courseStandardService;
        this.classService = classService;
    }

    @GetMapping("/{id}/view-curriculum-standard")
    public ResponseEntity<InputStreamResource> viewCurriculumStandard(@PathVariable Long id) {
        CourseStandard courseStandard = courseStandardService.getCourseStandardAhead();
        if (courseStandard == null || courseStandard.getContent() == null) {
            return ResponseEntity.status(404).body(null);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(courseStandard.getContent());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + courseStandard.getTitle())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(courseStandard.getContent().length)
                .body(resource);
    }

    @PostMapping("/{id}/create-class")
    public ResponseEntity<TeacherCreateClassResponse> createClass(@PathVariable Long id, @RequestBody TeacherCreateClassRequest request) {

    }
}
