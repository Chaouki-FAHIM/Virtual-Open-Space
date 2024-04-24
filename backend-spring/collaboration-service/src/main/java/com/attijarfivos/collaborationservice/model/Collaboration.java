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
    @NotNull
    private String titre;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;
    @NotNull
    private Boolean confidentielle;
    @NotNull
    private Long proprietaire;
    private Long invites;
    @NotNull
    private Boolean visible;
}
