package com.attijarivos.exception;

public class NotFoundDataException extends Exception {
    public NotFoundDataException(String id) {
        super("Team avec id "+id+" est non trouvé !!");
    }

    public NotFoundDataException(String element, String id) {
        super(element+" avec id "+id+" est non trouvé !!");
    }
}