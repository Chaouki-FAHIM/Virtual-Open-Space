package com.attijarivos.unit;

import com.attijarivos.dto.MembreResponse;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.MembreMapper;
import com.attijarivos.dto.MembreRequest;
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
    void createMembreWithValidData() throws RequiredDataException {

        // Arrange
        MembreRequest request = MembreRequest.builder()
                .nom("FAHIM")
                .prenom("Chaouki")
                .roleHabilation(RoleHabilation.TEST)
                .build();

        Membre membre = new Membre();

        MembreResponse expectedResponse = new MembreResponse();

        when(membreMapper.fromReqToMembre(any(MembreRequest.class))).thenReturn(membre);
        when(membreRespository.save(any(Membre.class))).thenReturn(membre);
        when(membreMapper.fromMembreToRes(any(Membre.class))).thenReturn(expectedResponse);

        // Act
        MembreResponse response = membreService.createMembre(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void createMembreWithRequiredDataFirstName() {
        // Arrange
        MembreRequest requestWithMissingNom = MembreRequest.builder()
                .nom(null)
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
                .nom("FAHIM")
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
                .nom("KHAROUBI")
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