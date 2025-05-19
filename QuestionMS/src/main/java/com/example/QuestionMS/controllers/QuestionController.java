package com.example.QuestionMS.controllers;

import com.example.QuestionMS.models.Answer;
import com.example.QuestionMS.models.Question;
import com.example.QuestionMS.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {

        this.questionService = questionService;
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions(){
     return questionService.getallQuestions();
    }
   @PostMapping("/createQuestion")
    public String createQuestion(@RequestBody Question question){
         questionService.createQuestion(question);
       return "Question was created.";
   }
    @DeleteMapping("/deleteQuestion/{id}")
    public Question deleteQuestion(@PathVariable String id){
        return questionService.deleteQuestion(id);
    }
    @PutMapping("/updateQuestion/{id}")
    public Question updateQuestion(@RequestBody Question question , @PathVariable String id){
        return questionService.updateQuestion(question,id);
    }

    @GetMapping("/{id}")
    public Question getQuestionbyId(@PathVariable String id){
      return  questionService.getQuestionById(id);
    }

    @GetMapping("/{questionId}/answers")
    public List<Answer> getAnswers(@PathVariable String questionId,
                                   @RequestParam(required = false, defaultValue = "none") String sortBy){
        return questionService.getAnswersForQuestion(questionId,sortBy);
    }

    @PostMapping("/{questionId}/addanswer")
    public List<Answer> addAnswers(@PathVariable String questionId,@RequestBody Answer answer){
        return questionService.addAnswerToQuestion(questionId,answer);
    }


    @DeleteMapping("/{questionId}/deleteanswer/{answerId}")
    public List<Answer> deleteAnswers(@PathVariable String questionId,@PathVariable String answerId){
        return questionService.deleteAnswerFromQuestion(questionId,answerId);
    }

    @PostMapping("/{questionId}/updateanswer/{answerId}")
    public List<Answer> updateAnswers(@PathVariable String questionId,
                                      @PathVariable String answerId,
                                      @RequestBody String content){
        return questionService.updateAnswerInQuestion(questionId,answerId,content);
    }

    @PutMapping("/{questionId}/answers/{answerId}/rate")
    public Answer rateAnswer(@PathVariable String questionId,
                             @PathVariable String answerId,
                             @RequestParam int rating){

        return questionService.rateAnswer(questionId,answerId,rating);
    }

    @PutMapping("/{questionId}/answers/{answerId}/deleterate")
    public Answer deleterateAnswer(@PathVariable String questionId,
                             @PathVariable String answerId,
                             @RequestParam int rating){

        return questionService.deleterateAnswer(questionId,answerId,rating);
    }

    @PutMapping("/{questionId}/answers/{answerId}/updaterate")
    public Answer updaterateAnswer(@PathVariable String questionId,
                                   @PathVariable String answerId,
                                   @RequestParam int oldrating,
                                   @RequestParam int newrating){

        return questionService.updaterateAnswer(questionId,answerId,oldrating,newrating);
    }

    @PutMapping("/{questionId}/deleterate")
    public Question deleterateQuestion(@PathVariable String questionId,
                                   @RequestParam int rating){

        return questionService.derateQuestion(questionId,rating);
    }

    @PutMapping("/{questionId}/rate")
    public Question rateQuestion(@PathVariable String questionId,
                                       @RequestParam int rating){

        return questionService.rateQuestion(questionId,rating);
    }
}
