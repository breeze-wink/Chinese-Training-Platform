package com.example.dto.request.system;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSchoolAdminRequest {
    private String name;
    private String password;
    private String schoolName;
}
