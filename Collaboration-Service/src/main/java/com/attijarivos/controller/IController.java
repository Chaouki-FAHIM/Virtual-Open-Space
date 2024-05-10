package com.attijarivos.controller;

import org.springframework.http.ResponseEntity;


public interface IController <Request, JoindreReq, ID> {

    ResponseEntity<?> createOne(Request request);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getOne(ID id);
    ResponseEntity<?> delete(ID id);
    ResponseEntity<?> joindre(ID id, JoindreReq joindreReq);
}
