package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(String element, Long id) {
        super(element+" avec l'id "+id+" est introuvable !!");
    }

    public NotFoundDataException(String msg) {
        super(msg);
    }
}