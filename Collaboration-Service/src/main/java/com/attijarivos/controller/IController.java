package com.attijarivos.controller;

import com.attijarivos.exception.*;
import org.springframework.http.ResponseEntity;


public interface IController <Request, ID> {

    ResponseEntity<?> createOne(Request request) throws RequiredDataException, NotFoundDataException, MicroserviceAccessFailureException, RededicationInvitationException, NotValidOwnerInviteException;
    ResponseEntity<?> getAll() throws NotFoundDataException;
    ResponseEntity<?> getOne(ID id) throws NotFoundDataException;
    ResponseEntity<?> delete(ID id) throws NotFoundDataException;
}
