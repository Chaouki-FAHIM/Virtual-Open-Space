package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(String id) {
        super("Team avec id "+id+" est non trouvé !!");
    }
}