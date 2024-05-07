package com.attijarivos.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="collaboration")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Collaboration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCollaboration;

    @Column(nullable = false)
    private String titre;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreationCollaboration;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;

    @Column(nullable = false)
    private Boolean confidentielle;

    @Column(name = "id_proprietaire",nullable = false)
    private String IdProprietaire;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean visible;
}
