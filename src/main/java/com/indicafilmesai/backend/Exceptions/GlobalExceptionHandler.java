package com.indicafilmesai.backend.Exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.indicafilmesai.backend.Responses.ResponseError;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ResponseError>  handleBadRequestException(
            BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseError>  handleDataAccessException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseError(ex.getMessage())); // Status 409 Conflict
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError(ex.getMessage()));
        }
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ResponseError>  handleJWTCreationException(
            JWTCreationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(ex.getMessage()));
    }


    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ResponseError> handleJWTCreationException(
            JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseError(ex.getMessage()));
    }


}
