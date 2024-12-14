package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetPapersResponse {
    private String message;
    private List<PaperInfo> papers;
    @Setter
    @Getter
    public static class PaperInfo {
        private Long id;
        private String name;
        private Integer totalScore;
        private String createTime;
        private Double difficulty;
    }
}
