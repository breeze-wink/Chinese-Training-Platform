package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetSchoolAdminAccountsResponse {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long schoolAdminId;
        private String userName;
        private String name;
        private String email;
        private String schoolName;
    }
}
