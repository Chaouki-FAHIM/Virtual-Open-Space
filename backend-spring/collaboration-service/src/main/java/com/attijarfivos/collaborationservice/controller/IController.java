package com.attijarfivos.collaborationservice.controller;

import com.attijarfivos.collaborationservice.model.Collaboration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IController <Request, ID>{

    @PostMapping
    ResponseEntity<?> create(Request request);

    ResponseEntity<?> getAll();
    ResponseEntity<?> getOne(ID id);
    ResponseEntity<?> update(ID id,Request request);
    ResponseEntity<?> delete(ID id,Request request);
}
