package com.example.controller;

import com.example.dto.response.StudentBusinessController.GetFinishedPractices;
import com.example.dto.response.StudentBusinessController.GetUnfinishedPractices;
import com.example.model.question.Practice;
import com.example.service.question.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentBusinessController {
    private final PracticeService practiceService;

    @Autowired
    public StudentBusinessController (PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping("/{id}/get-unfinished-practice-list")
    public ResponseEntity<GetUnfinishedPractices> getUnfinishedPractices(@PathVariable Long id) {
        GetUnfinishedPractices response = new GetUnfinishedPractices();
        List<Practice> practices = practiceService.getPracticesByStudentId(id);
        List<GetUnfinishedPractices.InfoData> data = new ArrayList<>();
        for(Practice practice : practices){
            if(practice.getPracticeTime() == null){
                GetUnfinishedPractices.InfoData infoData = new GetUnfinishedPractices.InfoData();
                infoData.setPracticeId(practice.getId());
                infoData.setPracticeName(practice.getName());
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("未完成作业列表获取成功");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/get-finished-practice-list")
    public ResponseEntity<GetFinishedPractices> getFinishedPractices(@PathVariable Long id) {
        GetFinishedPractices response = new GetFinishedPractices();
        List<Practice> practices = practiceService.getPracticesByStudentId(id);
        List<GetFinishedPractices.InfoData> data = new ArrayList<>();
        for(Practice practice : practices){
            if(practice.getPracticeTime() != null){
                GetFinishedPractices.InfoData infoData = new GetFinishedPractices.InfoData();
                infoData.setPracticeId(practice.getId());
                infoData.setPracticeName(practice.getName());
                infoData.setPracticeTime(practice.getPracticeTime().toString());
                infoData.setTotalScore(practice.getTotalScore().doubleValue());
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("已完成作业列表获取成功");
        return ResponseEntity.ok(response);
    }

}
