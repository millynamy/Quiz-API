package com.cooksys.quiz_api.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Quiz {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @OneToMany(mappedBy = "quiz")
  private List<Question> questions;

  // Helper method to add a question to a quiz
  public void addQuestion(Question question) {
    questions.add(question);
    question.setQuiz(this);
  }

  // Helper method to remove a question
 // public void removeQuestion(Question question) {
 //   questions.remove(question);
  //  question.setQuiz(null);

}
