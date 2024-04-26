package com.attijarfivos.collaborationservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;

    @Column(nullable = false)
    private Boolean confidentielle;

    @Column(nullable = false)
    private Long proprietaire;

    private Long invites;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean visible;

}
