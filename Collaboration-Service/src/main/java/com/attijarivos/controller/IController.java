package com.attijarivos.controller;

import org.springframework.http.ResponseEntity;


public interface IController <Request,ID>{

    ResponseEntity<?> create(Request request);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getOne(ID id);
    ResponseEntity<?> update(ID id,Request request);
    ResponseEntity<?> delete(ID id);
}
