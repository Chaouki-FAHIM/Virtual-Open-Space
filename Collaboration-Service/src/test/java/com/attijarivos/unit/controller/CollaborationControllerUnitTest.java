package com.attijarivos.unit.controller;

import com.attijarivos.ICollaborationTest;
import com.attijarivos.controller.InvitationController;
import com.attijarivos.repository.CollaborationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


public class CollaborationControllerUnitTest implements ICollaborationTest {

    @Mock
    private CollaborationRepository collaborationRepository;
    @InjectMocks
    private InvitationController invitationController;


    @Test
    void createCollaborationWithValidData() {
    }

    @Test
    void createCollaborationWithRequiredTitleData() {
    }

    @Test
    void createCollaborationWithRequiredSOwnerIdData() {
    }

    @Test
    void createCollaborationWithRequiredStateData() {
    }

}
