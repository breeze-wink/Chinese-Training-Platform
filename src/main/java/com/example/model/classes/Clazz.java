package com.example.model.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clazz {
    private Long id;
    private String name;
    private String description;
    private String inviteCode;
    private Long creatorId;
    private Long schoolId;

}