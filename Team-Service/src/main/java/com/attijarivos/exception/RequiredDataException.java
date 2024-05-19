package com.attijarivos.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequiredDataException extends Exception {

    public RequiredDataException(String field, String context, String object) {
        super(field+" est obligatoire pour "+context+" "+object);
    }

    public RequiredDataException(String field, String context, String object, int numbreOfObjectProcessed) {
        super(field+" est obligatoire pour "+context+" "+object+ " numéro "+numbreOfObjectProcessed);
    }

    public RequiredDataException(String message) {
        super(message);
    }
}

