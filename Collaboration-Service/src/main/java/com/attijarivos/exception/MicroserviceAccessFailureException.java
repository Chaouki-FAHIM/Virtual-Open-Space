package com.attijarivos.exception;

import java.io.IOException;

public class MicroserviceAccessFailureException extends IOException {

    public MicroserviceAccessFailureException(String nameService) {
        super("Problème lors de connexion avec le Micro-Service " + nameService);
    }
}
