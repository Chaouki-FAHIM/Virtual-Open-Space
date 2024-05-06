package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(Long id) {
        super("Collaboration avec id "+id+" est non trouvée !!");
    }
}