package com.example.QuestionMS.services;

import com.example.QuestionMS.DTO.QuestionEvent;
import com.example.QuestionMS.models.Answer;
import com.example.QuestionMS.models.Question;
import com.example.QuestionMS.rabbitmq.RabbitMQConfig3;
import com.example.QuestionMS.rabbitmq.RabbitMQProducer2;
import com.example.QuestionMS.repositories.QuestionRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    private final AmqpTemplate rabbit;
    private final QuestionRepository questionRepository;
    private final RabbitMQProducer2 rabbitMQProducer2;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           AmqpTemplate rabbit,
                           RabbitMQProducer2 rabbitMQProducer2) {
        this.rabbit = rabbit;
        this.questionRepository = questionRepository;
        this.rabbitMQProducer2 = rabbitMQProducer2;
    }

    public void createQuestion(Question question) {
        question.setCreatedTime(LocalDateTime.now());

        rabbit.convertAndSend(
                RabbitMQConfig3.EXCHANGE,
                RabbitMQConfig3.CREATED_KEY,
                new QuestionEvent(question.getId(), "QUESTION_CREATED", Instant.now())
        );

        questionRepository.save(question);
        this.rabbitMQProducer2.sendToNotifyPosting(question.getId());
    }

    public Question deleteQuestion(String id) {
        rabbit.convertAndSend(
                RabbitMQConfig3.EXCHANGE,
                RabbitMQConfig3.DELETED_KEY,
                new QuestionEvent(id, "QUESTION_DELETED", Instant.now())
        );
        questionRepository.deleteById(id);
        return null;
    }

    public List<Question> getallQuestions() {
        return questionRepository.findAll();
    }

    public Question updateQuestion(Question question, String id) {
        return questionRepository.findById(id).map(existing -> {
            existing.setTitle(question.getTitle());
            existing.setContent(question.getContent());

            rabbit.convertAndSend(
                    RabbitMQConfig3.EXCHANGE,
                    RabbitMQConfig3.UPDATED_KEY,
                    new QuestionEvent(id, "QUESTION_UPDATED", Instant.now())
            );

            return questionRepository.save(existing);
        }).orElse(null);
    }

    public List<Answer> addAnswerToQuestion(String questionId, Answer answer) {
        return questionRepository.findById(questionId).map(question -> {
            answer.setCreatedDate(LocalDateTime.now());
            answer.setId(UUID.randomUUID().toString().replace("-", ""));
            question.getAnswers().add(answer);

            rabbit.convertAndSend(
                    RabbitMQConfig3.EXCHANGE,
                    RabbitMQConfig3.ANSWER_KEY,
                    new QuestionEvent(questionId, "QUESTION_ANSWER_ADDED", Instant.now())
            );

            return questionRepository.save(question).getAnswers();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found"));
    }

    public List<Answer> deleteAnswerFromQuestion(String questionId, String answerId) {
        return questionRepository.findById(questionId).map(question -> {
            question.getAnswers().removeIf(ans -> ans.getId().equals(answerId));
            return questionRepository.save(question).getAnswers();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found"));
    }

    public List<Answer> updateAnswerInQuestion(String questionId, String answerId, String content) {
        return questionRepository.findById(questionId).map(question -> {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    answer.setContent(content);

                    rabbit.convertAndSend(
                            RabbitMQConfig3.EXCHANGE,
                            RabbitMQConfig3.UPDATED_KEY,
                            new QuestionEvent(questionId, "QUESTION_UPDATED", Instant.now())
                    );

                    return questionRepository.save(question).getAnswers();
                }
            }
            return null;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found"));
    }

    public List<Answer> getAnswersForQuestion(String questionId, String sortBy) {
        return questionRepository.findById(questionId).map(question -> {
            List<Answer> answers = new ArrayList<>(question.getAnswers());

            switch (sortBy.toLowerCase()) {
                case "latest" -> answers.sort(Comparator.comparing(Answer::getCreatedDate).reversed());
                case "earliest" -> answers.sort(Comparator.comparing(Answer::getCreatedDate));
                case "rating" -> answers.sort(Comparator.comparing(Answer::getRating).reversed());
            }

            return answers;
        }).orElse(Collections.emptyList());
    }

    public Answer rateAnswer(String questionId, String answerId, int rating) {
        return questionRepository.findById(questionId).map(question -> {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    float total = answer.getRating() * answer.getRatingCount() + rating;
                    answer.setRatingCount(answer.getRatingCount() + 1);
                    answer.setRating(total / answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
            return null;
        }).orElse(null);
    }

    public Answer deleterateAnswer(String questionId, String answerId, int rating) {
        return questionRepository.findById(questionId).map(question -> {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    float total = answer.getRating() * answer.getRatingCount() - rating;
                    answer.setRatingCount(answer.getRatingCount() - 1);
                    answer.setRating(total / answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
            return null;
        }).orElse(null);
    }

    public Answer updaterateAnswer(String questionId, String answerId, int oldRating, int newRating) {
        return questionRepository.findById(questionId).map(question -> {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(answerId)) {
                    float total = answer.getRating() * answer.getRatingCount() - oldRating + newRating;
                    answer.setRating(total / answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
            return null;
        }).orElse(null);
    }

    public Question rateQuestion(String questionId, int rating) {
        return questionRepository.findById(questionId).map(question -> {
            float total = question.getRating() * question.getRatingCount() + rating;
            question.setRatingCount(question.getRatingCount() + 1);
            question.setRating(total / question.getRatingCount());
            return questionRepository.save(question);
        }).orElse(null);
    }

    public Question derateQuestion(String questionId, int rating) {
        return questionRepository.findById(questionId).map(question -> {
            float total = question.getRating() * question.getRatingCount() - rating;
            question.setRatingCount(question.getRatingCount() - 1);
            question.setRating(total / question.getRatingCount());
            return questionRepository.save(question);
        }).orElse(null);
    }

    public Question getQuestionById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found"));
    }

    @RabbitListener(queues = RabbitMQConfig3.Question_Queue)
    public void notifyPosting(String id) {
        System.out.println("Received notifyPosting event with ID: " + id);
    }
}
