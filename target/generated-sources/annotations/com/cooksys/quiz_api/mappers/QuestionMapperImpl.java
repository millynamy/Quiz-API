package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.AnswerRequestDto;
import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-26T19:41:12-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public QuestionResponseDto entityToDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setId( question.getId() );
        questionResponseDto.setText( question.getText() );
        questionResponseDto.setAnswers( answerMapper.entitiesToDtos( question.getAnswers() ) );

        return questionResponseDto;
    }

    @Override
    public List<QuestionResponseDto> entitiesToDtos(List<Question> entities) {
        if ( entities == null ) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>( entities.size() );
        for ( Question question : entities ) {
            list.add( entityToDto( question ) );
        }

        return list;
    }

    @Override
    public Question dtoToEntity(QuestionRequestDto questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setText( questionRequestDto.getText() );
        question.setAnswers( answerRequestDtoListToAnswerList( questionRequestDto.getAnswers() ) );

        return question;
    }

    protected Answer answerRequestDtoToAnswer(AnswerRequestDto answerRequestDto) {
        if ( answerRequestDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setText( answerRequestDto.getText() );
        answer.setCorrect( answerRequestDto.isCorrect() );

        return answer;
    }

    protected List<Answer> answerRequestDtoListToAnswerList(List<AnswerRequestDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Answer> list1 = new ArrayList<Answer>( list.size() );
        for ( AnswerRequestDto answerRequestDto : list ) {
            list1.add( answerRequestDtoToAnswer( answerRequestDto ) );
        }

        return list1;
    }
}
