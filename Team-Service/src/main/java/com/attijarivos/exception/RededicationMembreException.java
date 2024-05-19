package com.attijarivos.exception;

public class RededicationMembreException extends Exception {

    public RededicationMembreException() {
        super("Membre a déjà été enregistré dans cette équipe");
    }

    public RededicationMembreException(int numbreOfEquipeProcessedForAddList) {
        super("Membre numéro "+numbreOfEquipeProcessedForAddList+" a déjà été enregistré dans cette équipe");
    }

}
