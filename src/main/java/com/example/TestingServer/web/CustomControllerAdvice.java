package com.example.TestingServer.web;

import com.example.TestingServer.exceptions.*;
import com.example.TestingServer.payload.response.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@CrossOrigin
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> handleUserExist(UserExistException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check credentials");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check username");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<Object> handleReportNotFoundException(ReportNotFoundException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check credentials");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<Object> handleAnswerNotFound(AnswerNotFoundException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check username");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFound(QuestionNotFoundException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check username");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReportAnswerNotFoundException.class)
    public ResponseEntity<Object> handleReportAnswerNotFound(ReportAnswerNotFoundException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        ResponseError responseError = new ResponseError(message, "Please check username");
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest webRequest){
        ResponseError responseError = new ResponseError("Malformed JSON Request", ex.getMessage());
        return new ResponseEntity(responseError, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseError responseError = new ResponseError("Validation failed", "Please check credentials", errors);
        return new ResponseEntity<>(responseError, status);
    }

}