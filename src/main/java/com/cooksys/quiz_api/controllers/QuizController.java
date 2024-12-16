package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }

  // TODO: Implement the remaining 6 endpoints from the documentation.

  //POST quiz Creates a quiz and adds to collection
  //Returns the Quiz that it created
  @PostMapping
  public QuizResponseDto createQuiz(@RequestBody Quiz quiz){

    return quizService.createQuiz(quiz);
  }

  //DELETE quiz/{id} Deletes the specified quiz from collection
  //Returns the deleted Quiz
  @DeleteMapping("/{id}")
  public QuizResponseDto deleteQuiz(@PathVariable Long id){

    return quizService.deleteQuiz(id);
  }

  //PATCH quiz/{id}/rename/{newName} Rename the specified quiz using the new name given
  //Returns the renamed Quiz
  @PatchMapping("/{id}/rename/{newName}")
  public QuizResponseDto renameQuiz(@PathVariable Long id, @PathVariable String newName){
    return quizService.renameQuiz(id, newName);
  }

  //GET quiz/{id}/random
  //Returns a random Question from the specified quiz
  @GetMapping("/{id}/random")
  public QuestionResponseDto getRandomQuestion(@PathVariable Long id) {
    return quizService.getRandomQuestion(id);
  }

  //PATCH quiz/{id}/add Adds a question to the specified quiz
  //Receives a Question
  //Returns the modified Quiz
  @PatchMapping("/{id}/add")
  public QuizResponseDto addQuestion(@PathVariable Long quizId, @RequestBody QuestionRequestDto questionRequestDto){
    return quizService.addQuestion(quizId, questionRequestDto);
  }

//DELETE quiz/{id}/delete/{questionID} Deletes the specified question from the specified quiz
//
//Returns the deleted Question
@DeleteMapping("/{id}/delete/{questionId}")
public QuestionResponseDto deleteQuestion(@PathVariable("id") Long quizId, @PathVariable("questionId")Long questionId){
  return quizService.deleteQuestion(quizId, questionId);
}
}
