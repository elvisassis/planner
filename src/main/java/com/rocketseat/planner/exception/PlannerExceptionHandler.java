package com.rocketseat.planner.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class PlannerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public PlannerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        problemDetail.setDetail(createListErros(ex.getBindingResult()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        String erros = createListErros(e);
        problemDetail.setDetail(erros);
        return problemDetail;
    }

    @ExceptionHandler(StartAtCannotBeBiggerEndsAtException.class)
    public ProblemDetail handleStartAtCannotBeBiggerEndsAtException(StartAtCannotBeBiggerEndsAtException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    //Cria uma lista de erros de ConstraintViolationException
    private String createListErros(ConstraintViolationException e) {
        String errors = null;
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.stream()
                .map(constraintViolation -> String.format(messageSource.getMessage("validation.constraints.NotNull.message", null,
                        LocaleContextHolder.getLocale()), constraintViolation.getPropertyPath()))
                .toList());
        errors = String.join(", ", messages);
        return errors;
    }

    //Cria uma lista de erros de MethodArgumentNotValidException
    private String createListErros(BindingResult bindingResult) {
        String messageErrors = null;
        List<String> messages = new ArrayList<>(bindingResult.getFieldErrors().stream()
                .map(fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())).toList());
        messageErrors = String.join(", ", messages);
        return messageErrors;
    }

}
