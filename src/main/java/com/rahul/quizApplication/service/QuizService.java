package com.rahul.quizApplication.service;

import com.rahul.quizApplication.dao.QuestionDao;
import com.rahul.quizApplication.dao.QuizDao;
import com.rahul.quizApplication.model.Question;
import com.rahul.quizApplication.model.QuestionWrapper;
import com.rahul.quizApplication.model.Quiz;
import com.rahul.quizApplication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createRandomQuizByCategory(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(int id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDB=quiz.get().getQuestion();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();

        for(Question q:questionsFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int quizId, List<Response> responses) {
        Quiz quiz=quizDao.findById(quizId).get();
        System.out.println(responses.get(0).getQuestionId());
        List<Question> questions=quiz.getQuestion();
        int i=0;
        int rightAnswers=0;
        for(Response res:responses){
            if(res.getResponse().equalsIgnoreCase(questions.get(i).getRightAnswer())){
                rightAnswers++;
            }
            i++;
        }
        return new ResponseEntity<>(rightAnswers,HttpStatus.OK);

    }
}
