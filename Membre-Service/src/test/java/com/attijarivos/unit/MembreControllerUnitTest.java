package com.attijarivos.unit;

import com.attijarivos.controller.MembreController;
import com.attijarivos.dto.MembreRequest;
import com.attijarivos.dto.MembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.MembreService;
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
    public void shouldCreateMembreSuccessfully() throws RequiredDataException, MicroserviceAccessFailureException {
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
        when(membreService.getOneMembreDetail(membreId)).thenReturn(expectedMembre);

        // Act
        ResponseEntity<?> response = membreController.getMembre(membreId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMembre, response.getBody());
    }

}
