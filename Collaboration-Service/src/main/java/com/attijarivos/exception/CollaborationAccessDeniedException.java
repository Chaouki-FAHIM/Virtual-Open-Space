package com.attijarivos.exception;


public class CollaborationAccessDeniedException extends Exception {

    public CollaborationAccessDeniedException(Long idCollaboration) {
        super("Autorisation d'acces à la collaboration confidentielle d'id "+idCollaboration);
    }
}
