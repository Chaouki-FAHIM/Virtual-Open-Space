package com.attijarivos.exception;

public class RededicationInvitationException extends Exception {

    public RededicationInvitationException() {
        super("Invitation a déjà été envoyée pour cette collaboration en ligne");
    }

    public RededicationInvitationException(int numbreOfInvitationProcessedForCreateList) {
        super("Invitation numéro "+numbreOfInvitationProcessedForCreateList+" a déjà été envoyée pour cette collaboration en ligne");
    }

}
