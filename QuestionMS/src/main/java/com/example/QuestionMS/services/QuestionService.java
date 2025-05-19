package com.example.QuestionMS.services;

import com.example.QuestionMS.rabbitmq.RabbitMQConfig;
import com.example.QuestionMS.models.Answer;
import com.example.QuestionMS.models.Question;
import com.example.QuestionMS.rabbitmq.RabbitMQProducer2;
import com.example.QuestionMS.repositories.QuestionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private RabbitMQProducer2 rabbitMQProducer2;
    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public String createQuestion(Question question){
        question.setCreatedTime(LocalDateTime.now());
        questionRepository.save(question);
        this.rabbitMQProducer2.sendToNotifyPosting(question.getId());
        return "Question " + question.getId() + "is on queue";
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

    @RabbitListener(queues = RabbitMQConfig.POSTING_QUEUE)
    public void notifyPosting(String id) {
        System.out.println("Received order: " + id);
    }
}



