// PreAssembledQuestion.java
package com.example.dto.redis;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PreAssembledQuestion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private Long id;
    private String questionBody;
    private String type;
    private List<SubQuestion> subQuestions;


}


