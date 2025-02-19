package com.example.vet.core.config;

import com.example.vet.core.exceptions.AlreadyExistsException;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Result> handleAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }

}
