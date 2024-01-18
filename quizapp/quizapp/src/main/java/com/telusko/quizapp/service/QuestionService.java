package com.telusko.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quizapp.dao.QuestionsDAO;
import com.telusko.quizapp.model.Question;

@Service
public class QuestionService {
	@Autowired
	QuestionsDAO questionsDAO;
	public ResponseEntity<List<Question>> getAllQuestions() {
		try { 
		return new ResponseEntity<>(questionsDAO.findAll(),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			}
	return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);	
	}
	
	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
		try {
		return new ResponseEntity<>(questionsDAO.findByCategory(category), HttpStatus.OK);
		} catch(Exception e) {e.printStackTrace();}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> addQuestion(Question question) {
		try{questionsDAO.save(question);
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
		}
		catch(Exception e) {e.printStackTrace();}
		return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<String> deleteQuestion(int id) {
		try{ questionsDAO.deleteById(id);
		return new ResponseEntity<>("Success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();}
		return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
	}
}
