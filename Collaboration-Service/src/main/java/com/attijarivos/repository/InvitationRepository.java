package com.attijarivos.repository;


import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findByCollaboration(Collaboration collaboration);
}
