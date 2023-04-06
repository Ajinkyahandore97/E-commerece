package com.project.Ecommerce.exception;

import com.project.Ecommerce.payload.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

        log.info("Exception Handler Invoked");

        ApiResponseMessage message = ApiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>illegalArgumentException(MethodArgumentNotValidException ex){

        log.info("Method Argument Exception!!");

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String ,Object> response= new HashMap<>();

        allErrors.stream().forEach(objectError -> {

            String message = objectError.getDefaultMessage();

            String field = ((FieldError) objectError).getField();

            response.put(field , message);

        });

        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage>resourceBadApiRequest(BadApiRequestException ex){

        log.info("Bad Api Request");

        ApiResponseMessage message = ApiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
