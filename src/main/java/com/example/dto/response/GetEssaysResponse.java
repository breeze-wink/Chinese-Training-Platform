package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetEssaysResponse {
    private String message;
    private List<InfoData> infoData;

    @Getter
    @Setter
    public static class InfoData {
        private Long id;
        private String title;
    }
}
