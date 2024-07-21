package com.example.ecommerce.shared;

import com.example.ecommerce.shared.exception.GenericException;
import com.example.ecommerce.shared.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<DefaultHttpResponse<String>> handleGenericException(GenericException ex, WebRequest request) {
        DefaultHttpResponse<String> response = new DefaultHttpResponse<>(ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<DefaultHttpResponse<String>> handleMessageException(MessageException ex, WebRequest request) {
        DefaultHttpResponse<String> response = new DefaultHttpResponse<>(ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultHttpResponse<String>> handleGlobalException(Exception ex, WebRequest request) {
        DefaultHttpResponse<String> response = new DefaultHttpResponse<>(ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultHttpResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("MENSAGEM.ERRO-VALIDACAO");

        DefaultHttpResponse<String> response = new DefaultHttpResponse<>(message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
