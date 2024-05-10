package com.attijarivos.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @Column(nullable = false,length = 50)
    private String titre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreationCollaboration;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;

    @Column(nullable = false)
    private Boolean confidentielle;

    @Column(name = "id_proprietaire",nullable = false)
    private String IdProprietaire;

    @Column(nullable = false)
    private Boolean visible= true;;

    @OneToMany(mappedBy = "collaboration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations;
}
