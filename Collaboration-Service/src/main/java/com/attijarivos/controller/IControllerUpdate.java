package com.attijarivos.controller;

import org.springframework.http.ResponseEntity;

public interface IControllerUpdate<Request,ID> {
    ResponseEntity<?> update(ID id, Request request);
}
