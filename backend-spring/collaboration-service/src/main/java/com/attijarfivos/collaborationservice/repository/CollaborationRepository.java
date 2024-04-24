package com.attijarfivos.collaborationservice.repository;

import com.attijarfivos.collaborationservice.model.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {

}
