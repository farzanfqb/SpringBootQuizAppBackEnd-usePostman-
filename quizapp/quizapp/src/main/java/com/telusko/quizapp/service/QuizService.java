package com.telusko.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quizapp.dao.QuestionsDAO;
import com.telusko.quizapp.dao.QuizDAO;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionWrapper;
import com.telusko.quizapp.model.Quiz;
import com.telusko.quizapp.model.Response;
@Service
public class QuizService {
	
	@Autowired
	QuizDAO quizDAO; //create a table in database for later use 
	@Autowired
	QuestionsDAO questionsDAO;
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Question> questions = questionsDAO.findRandomQuestionsByCategory(category,numQ);
		Quiz quiz = new Quiz(); 
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDAO.save(quiz);
		return new ResponseEntity<>("success", HttpStatus.CREATED);

	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		// TODO Auto-generated method stub
		Optional<Quiz> quiz = quizDAO.findById(id);
		List<Question> questionFromDB = quiz.get().getQuestions();
		
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q : questionFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestionTitle());
			questionsForUser.add(qw);
		}
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz = quizDAO.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		int i =0;
		for(Response response : responses) {
			if(response.getResponse().equals(questions.get(i).getCorrectAnswer()))
				right++;
			i++;
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
