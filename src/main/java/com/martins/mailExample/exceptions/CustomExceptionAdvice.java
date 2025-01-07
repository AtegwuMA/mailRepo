package com.martins.mailExample.exceptions;

import com.martins.mailExample.enums.ErrorCodes;
import com.martins.mailExample.response.GenericResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

    private ResponseEntity<GenericResponse> createResponse(ErrorCodes errorCode, String message, HttpStatus status) {
        GenericResponse response = new GenericResponse(errorCode.getCode(), message, status);
        return new ResponseEntity<>(response, status);
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<String> errors = ex.getBindingResult().getAllErrors().stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        GenericResponse response = new GenericResponse(ErrorCodes.VALIDATION_ERROR.getCode(),
//                "Validation error", HttpStatus.BAD_REQUEST, errors);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException: ", exception);
        return createResponse(ErrorCodes.VALIDATION_ERROR, "An unknown error occurred", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("ResourceNotFoundException: ", ex);
        return createResponse(ErrorCodes.RESOURCE_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

//
//@ExceptionHandler(ConstraintViolationException.class)
//public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException exception) {
//    log.error("ConstraintViolationException: ", exception);
//    return createResponse(ErrorCodes.VALIDATION_ERROR, ErrorCodes.VALIDATION_ERROR.getDescription(), HttpStatus.BAD_REQUEST);
//}
//
//@ExceptionHandler(ConstraintViolationException.class)
//public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException exception) {
//    log.error("ConstraintViolationException: ", exception);
//    return createResponse(ErrorCodes.VALIDATION_ERROR, ErrorCodes.VALIDATION_ERROR.getDescription(), HttpStatus.BAD_REQUEST);
//}
//
//@ExceptionHandler(MethodArgumentNotValidException.class)
//protected ResponseEntity<GenericResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
//    List<String> errors = ex.getBindingResult().getAllErrors().stream()
//            .map(error -> error.getDefaultMessage())
//            .collect(Collectors.toList());
//
//    GenericResponse response = new GenericResponse(ErrorCodes.VALIDATION_ERROR.getCode(), "Validation error", HttpStatus.BAD_REQUEST, errors);
//    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//}
//
//@ExceptionHandler(AppRequestException.class)
//public ResponseEntity<GenericResponse> handleAppRequestException(AppRequestException ex) {
//    log.error("AppRequestException: ", ex);
//    return createResponse(ErrorCodes.APP_REQUEST_ERROR, ErrorCodes.APP_REQUEST_ERROR.getDescription(), HttpStatus.BAD_REQUEST);
//}
//
//@ExceptionHandler(ResourceNotFoundException.class)
//public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//    log.error("ResourceNotFoundException: ", ex);
//    return createResponse(ErrorCodes.RESOURCE_NOT_FOUND, ErrorCodes.RESOURCE_NOT_FOUND.getDescription(), HttpStatus.NOT_FOUND);
//}


//
//public class ErrorCodes {
//    public static final String UNKNOWN_ERROR = "01";
//    public static final String VALIDATION_ERROR = "02";
//    public static final String APP_REQUEST_ERROR = "03";
//    public static final String RESOURCE_NOT_FOUND = "04";
//
//    // You can add more codes as needed
//}

//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.validation.ConstraintViolationException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@ControllerAdvice
//public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {
//
//    private ResponseEntity<GenericResponse> createResponse(String code, String message, HttpStatus status) {
//        GenericResponse response = new GenericResponse(code, message, status);
//        return new ResponseEntity<>(response, status);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException exception) {
//        log.error("ConstraintViolationException: ", exception);
//        return createResponse(ErrorCodes.VALIDATION_ERROR, "An Unknown Error", HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<GenericResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
//        List<String> errors = ex.getBindingResult().getAllErrors().stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        GenericResponse response = new GenericResponse(ErrorCodes.VALIDATION_ERROR, "Validation error", HttpStatus.BAD_REQUEST, errors);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(AppRequestException.class)
//    public ResponseEntity<GenericResponse> handleAppRequestException(AppRequestException ex) {
//        log.error("AppRequestException: ", ex);
//        return createResponse(ErrorCodes.APP_REQUEST_ERROR, ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        log.error("ResourceNotFoundException: ", ex);
//        return createResponse(ErrorCodes.RESOURCE_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//}


//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.validation.ConstraintViolationException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@ControllerAdvice
//public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {
//
//    private ResponseEntity<GenericResponse> createResponse(ErrorCodes errorCode, String message, HttpStatus status) {
//        GenericResponse response = new GenericResponse(errorCode.getCode(), message, status);
//        return new ResponseEntity<>(response, status);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException exception) {
//        log.error("ConstraintViolationException: ", exception);
//        return createResponse(ErrorCodes.VALIDATION_ERROR, "An unknown error occurred", HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<GenericResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
//        List<String> errors = ex.getBindingResult().getAllErrors().stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        GenericResponse response = new GenericResponse(ErrorCodes.VALIDATION_ERROR.getCode(), "Validation error", HttpStatus.BAD_REQUEST, errors);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(AppRequestException.class)
//    public ResponseEntity<GenericResponse> handleAppRequestException(AppRequestException ex) {
//        log.error("AppRequestException: ", ex);
//        return createResponse(ErrorCodes.APP_REQUEST_ERROR, ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        log.error("ResourceNotFoundException: ", ex);
//        return createResponse(ErrorCodes.RESOURCE_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//}