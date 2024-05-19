package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(String id) {
        super("Equipe avec id "+id+" est non trouvée !!");
    }

    public NotFoundDataException(String element, String id) {
        super(element+" avec id "+id+" est non trouvée !!");
    }
}