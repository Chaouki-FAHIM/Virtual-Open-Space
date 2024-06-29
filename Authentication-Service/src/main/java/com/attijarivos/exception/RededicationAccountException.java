package com.attijarivos.exception;

public class RededicationAccountException extends Exception {

    public RededicationAccountException() {
        super("Essayer de changé le username et/ou le mot de passe");
    }

}
