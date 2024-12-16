package com.cooksys.quiz_api.dtos;

import com.cooksys.quiz_api.entities.Answer;
import lombok.Data;
import java.util.List;

@Data
public class QuestionRequestDto {
    private String text;

    private List<AnswerRequestDto> answers;

}
