package com.marcel.malewski.playtogetherapi.entity.gamer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NewPasswordSameAsPrevious extends RuntimeException {
    public NewPasswordSameAsPrevious(String email) {
        super("The new password cannot be the same as the previous one");
    }
}
