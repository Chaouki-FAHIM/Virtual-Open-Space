package com.attijarivos.unit;

import com.attijarivos.dto.response.shorts.ShortMembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.MembreMapper;
import com.attijarivos.dto.request.MembreRequest;
import com.attijarivos.model.Membre;
import com.attijarivos.model.RoleHabilation;
import com.attijarivos.repository.MembreRespository;
import com.attijarivos.service.MembreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MembreServiceUnitTest {

    @Mock
    private MembreRespository membreRespository;
    @Mock
    private MembreMapper membreMapper;
    @InjectMocks
    private MembreService membreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMembreWithValidData() throws RequiredDataException, MicroserviceAccessFailureException {

        // Arrange
        MembreRequest request = MembreRequest.builder()
                .nomMembre("FAHIM")
                .prenom("Chaouki")
                .roleHabilation(RoleHabilation.TEST)
                .idTeam("ej888ejehks754")
                .build();

        Membre membre = new Membre();

        ShortMembreResponse expectedResponse = new ShortMembreResponse();

        when(membreMapper.fromReqToMembre(any(MembreRequest.class))).thenReturn(membre);
        when(membreRespository.save(any(Membre.class))).thenReturn(membre);
        when(membreMapper.fromMembreToRes(any(Membre.class))).thenReturn(expectedResponse);

        // Act
        ShortMembreResponse response = membreService.createMembre(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void createMembreWithRequiredDataFirstName() {
        // Arrange
        MembreRequest requestWithMissingNom = MembreRequest.builder()
                .nomMembre(null)
                .prenom("Chaouki")
                .roleHabilation(RoleHabilation.TEST)
                .build();

        // Act + Assert
        RequiredDataException exception = assertThrows(RequiredDataException.class, () -> membreService.createMembre(requestWithMissingNom));

        String expectedMessage = "Nom est obligatoire pour l'ajout d'un nouveau membre";
        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void createMembreWithRequiredDataLastName() {
        // Arrange
        MembreRequest requestWithMissingNom = MembreRequest.builder()
                .nomMembre("FAHIM")
                .prenom(null)
                .roleHabilation(RoleHabilation.TEST)
                .build();

        // Act + Assert
        RequiredDataException exception = assertThrows(RequiredDataException.class, () -> membreService.createMembre(requestWithMissingNom));

        String expectedMessage = "Prénom est obligatoire pour l'ajout d'un nouveau membre";
        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void createMembreWithRequiredDataRoleHabilation() {
        // Arrange
        MembreRequest requestWithMissingNom = MembreRequest.builder()
                .nomMembre("KHAROUBI")
                .prenom("Amine")
                .roleHabilation(null)
                .build();

        // Act + Assert
        RequiredDataException exception = assertThrows(RequiredDataException.class, () -> membreService.createMembre(requestWithMissingNom));

        String expectedMessage = "Rôle d'habilation est obligatoire pour l'ajout d'un nouveau membre";
        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}