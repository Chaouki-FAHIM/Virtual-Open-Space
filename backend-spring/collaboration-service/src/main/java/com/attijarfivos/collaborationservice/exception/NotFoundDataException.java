package com.attijarfivos.collaborationservice.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(Long id) {
        super("Collaboration avec id "+id+" est non trouvée !!");
    }
}