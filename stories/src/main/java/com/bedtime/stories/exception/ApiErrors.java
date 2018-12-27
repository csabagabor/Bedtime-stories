package com.bedtime.stories.exception;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {

    private List<String> message = new ArrayList<>();

    public ApiErrors(Errors errors) {
        List<ObjectError> errorList = errors.getAllErrors();
        for (ObjectError e : errorList) {
            message.add(e.getDefaultMessage());
        }
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
