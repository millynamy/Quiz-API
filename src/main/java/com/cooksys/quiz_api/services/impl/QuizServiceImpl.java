package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionMapper questionMapper;
  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public QuizResponseDto createQuiz(Quiz quiz) {

    return quizMapper.entityToDto(quizRepository.save(quiz));
  }

  @Override
  public QuizResponseDto deleteQuiz(Long id) {
    Quiz existingQuiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
    List<Question> questions = existingQuiz.getQuestions();
    for (Question question : questions) {
      List<Answer> answers = question.getAnswers();
      answerRepository.deleteAll(answers);
    }
    questionRepository.deleteAll(questions);
    quizRepository.delete(existingQuiz);
    return quizMapper.entityToDto(existingQuiz);
  }


  @Override
  public QuizResponseDto renameQuiz(Long id, String name) {
    Optional<Quiz> existingQuiz = quizRepository.findById(id);
    if(existingQuiz.isEmpty()){
      return null;
    }
  Quiz quiz = existingQuiz.get();
    quiz.setName(name);

    quiz = quizRepository.saveAndFlush(quiz);

    return quizMapper.entityToDto(quiz);
  }

  @Override
  public QuestionResponseDto getRandomQuestion(Long quizId) {
    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
    List<Question> questions = quiz.getQuestions();

    if (questions.isEmpty()) {
      throw new NoSuchElementException("No questions available for this quiz");
    }

    Random random = new Random();
    Question randomQuestion = questions.get(random.nextInt(questions.size()));
    return questionMapper.entityToDto(randomQuestion);
  }

  @Override
  public QuizResponseDto addQuestion(Long quizId, QuestionRequestDto questionRequestDto) {

    Optional<Quiz> existingQuiz = quizRepository.findById(quizId);
    if (existingQuiz.isEmpty()) {
      return null;
    }

    Quiz quiz = existingQuiz.get();

    Question newQuestion = questionMapper.dtoToEntity(questionRequestDto);

    if (newQuestion.getText() == null) {
      throw new IllegalArgumentException("Question text cannot be null or blank.");
    }
    newQuestion.setQuiz(quiz);

    boolean hasCorrectAnswer = false;
    for (Answer answer : newQuestion.getAnswers()) {
      if (answer.getText() == null) {
        throw new IllegalArgumentException("Answer text cannot be null or blank.");
      }
      if(answer.isCorrect()){
        hasCorrectAnswer = true;
      }
      answer.setQuestion(newQuestion);
    }

    if (!hasCorrectAnswer) {
      throw new IllegalArgumentException("At least one answer must be marked as correct.");
    }

    questionRepository.saveAndFlush(newQuestion);
    answerRepository.saveAllAndFlush(newQuestion.getAnswers());

    return quizMapper.entityToDto(quiz);
  }

  @Override
  public QuestionResponseDto deleteQuestion(Long id, Long questionId) {
    Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

    Question question = quiz.getQuestions().stream()
            .filter(q -> q.getId().equals(questionId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

    List<Answer> answers = question.getAnswers();
    answerRepository.deleteAll(answers);


    quiz.getQuestions().remove(question);
    question.setQuiz(null);
    questionRepository.delete(question);

    return questionMapper.entityToDto(question);
  }
}
