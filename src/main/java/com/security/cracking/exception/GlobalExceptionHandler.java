package com.security.cracking.exception;

import com.security.cracking.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice //anotacion para interceptar excepciones
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // generico
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e){
        ErrorResponseDTO errorRes = new ErrorResponseDTO();
        errorRes.setError(e.getMessage());
        return ResponseEntity.internalServerError().body(errorRes);
    }

    // TODO implementar @Valid en dto
    @ExceptionHandler(MethodArgumentNotValidException.class) // para los @Valid
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException  manve){
        return null;
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDTO> handleIOException(IOException ioe){
        ErrorResponseDTO errorRes = new ErrorResponseDTO();
        errorRes.setError(ioe.getMessage());
        errorRes.setMessage("Check file format");
        return ResponseEntity.status(422).body(errorRes);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException iae){
        ErrorResponseDTO errorRes = new ErrorResponseDTO();
        errorRes.setError(iae.getMessage());
        errorRes.setMessage("Try endpoint /hashtypes");
        return ResponseEntity.badRequest().body(errorRes);
    }

}
