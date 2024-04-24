package com.attijarivos.membreservice.unit;

import com.attijarivos.membreservice.controller.MembreController;
import com.attijarivos.membreservice.dto.MembreRequest;
import com.attijarivos.membreservice.dto.MembreResponse;
import com.attijarivos.membreservice.exception.NotFoundDataException;
import com.attijarivos.membreservice.exception.RequiredDataException;
import com.attijarivos.membreservice.model.RoleHabilation;
import com.attijarivos.membreservice.service.MembreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class MembreControllerUnitTest {

    @Mock
    private MembreService membreService;
    @InjectMocks
    private MembreController membreController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateMembreSuccessfully() throws RequiredDataException {
        // Arrange
        MembreRequest membreRequest = new MembreRequest();
        MembreResponse expectedResponse = new MembreResponse();
        when(
                membreService.createMembre(any(MembreRequest.class))
        ).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = membreController.createMembre(membreRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void shouldFetchAllMembresReturnEmptyList() {

        when(membreService.getAllMembres()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = membreController.getAllMembres();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }

    @Test
    public void shouldFetchOneMembreSuccessfully() throws NotFoundDataException {
        // Arrange
        String membreId = "1";
        MembreResponse expectedMembre = new MembreResponse();
        when(membreService.getMembre(membreId)).thenReturn(expectedMembre);

        // Act
        ResponseEntity<?> response = membreController.getMembre(membreId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMembre, response.getBody());
    }

}

