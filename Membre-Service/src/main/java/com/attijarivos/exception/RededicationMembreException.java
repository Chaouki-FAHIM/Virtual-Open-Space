package com.attijarivos.exception;

public class RededicationMembreException extends Exception {

    public RededicationMembreException(String element) {
        super("Membre avec "+element+" existe déjà");
    }

}
