package com.attijarivos;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public abstract class IntegrationTest {
     protected static final String FIRST_MEMBRE_ID = "663bbf3e7a25633f8b25a610";
     protected static final String SECOND_MEMBRE_ID = "663bbf3e7a25633f8b25a611";
     protected static final Long COLLABORATION_ID = 3L; // aleardy we have 2 collaborations
     protected final Long INVITATION_ID = 4L; // aleardy we have 3 invitations

     protected static final Date DATE = new Date();

}
