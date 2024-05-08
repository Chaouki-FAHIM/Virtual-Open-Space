package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(String element, Long id) {
        super(element+" avec id "+id+" est non trouvée !!");
    }
}