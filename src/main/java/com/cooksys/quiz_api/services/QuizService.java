package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Quiz;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();

  QuizResponseDto createQuiz(Quiz quiz);

  QuizResponseDto deleteQuiz(Long id);

  QuizResponseDto renameQuiz(Long id, String name);

  QuestionResponseDto getRandomQuestion( Long quizId);

  QuizResponseDto addQuestion(Long quizId, QuestionRequestDto questionRequestDto);


  QuestionResponseDto deleteQuestion(Long id, Long questionId);
}
