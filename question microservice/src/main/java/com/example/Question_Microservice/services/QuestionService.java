package com.example.Question_Microservice.services;

import com.example.Question_Microservice.models.Answer;
import com.example.Question_Microservice.models.Question;
import com.example.Question_Microservice.rabbitmq.RabbitMQProducer;
import com.example.Question_Microservice.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    public String createQuestion(Question question){
        question.setCreatedTime(LocalDateTime.now());
        questionRepository.save(question);

        String questionId = question.getId();
        this.rabbitMQProducer.sendToNotification(questionId);

        return "question created with ID: " + questionId;
    }

    public Question deleteQuestion(String id){
        questionRepository.deleteById(id);
        return null;
    }

    public List<Question> getallQuestions(){
       return questionRepository.findAll();
    }


    public Question updateQuestion(Question question , String id){
        if(questionRepository.existsById(id)){
            Question updatedQuestion = questionRepository.findById(id).get();
            updatedQuestion.setTitle(question.getTitle());
            updatedQuestion.setContent(question.getContent());


            return questionRepository.save(updatedQuestion);
        }

        return null;
    }

    public List<Answer> addAnswerToQuestion(String questionId, Answer answer){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            String iDans = UUID.randomUUID().toString().replace("-", "");
            Question question = questionOptional.get();
            answer.setCreatedDate(LocalDateTime.now());
            answer.setId(iDans);
            question.getAnswers().add(answer);
            return questionRepository.save(question).getAnswers();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }

    }

    public List<Answer> deleteAnswerFromQuestion(String questionId, String answerId){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)) {
                question.getAnswers().remove(answer);
                }
                }
           return questionRepository.save(question).getAnswers();

        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }

    }

    public List<Answer> updateAnswerInQuestion(String questionId, String answerId, String content){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)) {
                    answer.setContent(content);
                    return questionRepository.save(question).getAnswers();
                }
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }
    return null ;
    }

    public List<Answer> getAnswersForQuestion(String questionId,String sortBy){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            List<Answer> answers = questionOptional.get().getAnswers();

            return switch (sortBy.toLowerCase()) {
                case "latest" -> {
                    answers.sort(Comparator.comparing(Answer::getCreatedDate).reversed());
                    yield answers;
                }
                case "earliest" -> {
                    answers.sort(Comparator.comparing(Answer::getCreatedDate));
                    yield answers;
                }
                case "rating" -> {
                    answers.sort(Comparator.comparing(Answer::getRating).reversed());
                    yield answers;
                }
                default -> answers;
            };
        }
        return Collections.emptyList();
    }

public Answer rateAnswer(String questionId, String answerId, int rating){
    Optional<Question> questionOptional = questionRepository.findById(questionId);
    if(questionOptional.isPresent()){
        Question question = questionOptional.get();
        for(Answer answer : question.getAnswers()){
            if(answer.getId().equals(answerId)){
             float totalRating = answer.getRating() * answer.getRatingCount() + rating;
             answer.setRatingCount(answer.getRatingCount() + 1);
             answer.setRating(totalRating/ answer.getRatingCount());
             questionRepository.save(question);
             return answer;
            }
        }
    }
    return null;
}

    public Answer deleterateAnswer(String questionId, String answerId, int rating){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)){
                    float totalRating = answer.getRating() * answer.getRatingCount() - rating;
                    answer.setRatingCount(answer.getRatingCount() - 1);
                    answer.setRating(totalRating/ answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
        }
        return null;
    }

    public Answer updaterateAnswer(String questionId, String answerId, int oldrating, int newrating){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)){
                    float totalRating = answer.getRating() * answer.getRatingCount() - oldrating + newrating;
                    answer.setRating(totalRating/ answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
        }
        return null;
    }

    public Question rateQuestion(String questionId, int rating) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            float totalRating = question.getRating() * question.getRatingCount() + rating;
            question.setRatingCount(question.getRatingCount() + 1);
            question.setRating(totalRating / question.getRatingCount());
            return questionRepository.save(question);

        }
        return null ;
    }

    public Question derateQuestion(String questionId, int rating) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            float totalRating = question.getRating() * question.getRatingCount() - rating;
            question.setRatingCount(question.getRatingCount() - 1);
            question.setRating(totalRating / question.getRatingCount());
            return questionRepository.save(question);

        }
        return null ;
    }


    public Question getQuestionById(String id) {
      return  questionRepository.findById(id).get();

    }
}



