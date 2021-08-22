package hu.csontz.homework.message.receiver.controller.exceptionhandler;

import hu.csontz.homework.message.receiver.model.response.BadRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BadRequestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return BadRequestResponse.builder().withMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BadRequestResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return BadRequestResponse.builder().withMessage(ex.getMessage()).build();
    }
}
