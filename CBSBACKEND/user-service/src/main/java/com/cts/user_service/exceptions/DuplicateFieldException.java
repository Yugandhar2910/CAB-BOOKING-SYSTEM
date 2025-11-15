package com.cts.user_service.exceptions;
import lombok.Getter;

import java.util.*;

@Getter
public class DuplicateFieldException extends RuntimeException {

    private final Map<String, String> fieldErrors;

    public DuplicateFieldException(Map<String, String> fieldErrors) {
        super("Duplicate fields found");
        this.fieldErrors = fieldErrors;
    }

}
