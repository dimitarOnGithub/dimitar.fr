package fr.dimitar.web.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handle(MethodArgumentTypeMismatchException exception) {
        var errorDetails = new HashMap<String, String>();

        errorDetails.put("error", "Invalid query parameter provided");
        errorDetails.put("parameter", exception.getName());
        errorDetails.put("value", (String) exception.getValue());
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
