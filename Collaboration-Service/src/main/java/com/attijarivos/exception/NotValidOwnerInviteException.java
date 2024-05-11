package com.attijarivos.exception;

public class NotValidOwnerInviteException extends Exception{

    public NotValidOwnerInviteException(String idProprietaire) {
        super("L'identifiant de membre invité "+ idProprietaire + " est un identifiant de propriétaire ");
    }

    public NotValidOwnerInviteException(int numbreOfInvitationProcessedForCreateList) {
        super("Invitation numéro "+numbreOfInvitationProcessedForCreateList+ " contient l'identifiant de proriétaire de collaboration");
    }
}
