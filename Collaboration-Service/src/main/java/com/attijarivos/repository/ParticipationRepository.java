package com.attijarivos.repository;


import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByIdParticipantAndCollaboration(String idParticipant, Collaboration collaboration);
    List<Participation> findByCollaboration(Collaboration collaboration);
}
