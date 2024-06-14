package com.attijarivos.repository;


import com.attijarivos.model.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {

    Collaboration findByIdCollaboration(Long idCollaboration);
    List<Collaboration> findByTitreContainingIgnoreCase(String collaborationTitle);
}
