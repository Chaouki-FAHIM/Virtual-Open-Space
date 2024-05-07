package com.attijarivos.model;

import com.attijarivos.DTO.MembreResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="invitation")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInvitation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreationInvitation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateParticiaption;
    @ManyToOne
    @JoinColumn(name = "id_collaboration", referencedColumnName = "id")
    private Collaboration collaboration;

    @Column(name = "id_membre",nullable = false)
    private String IdMembre;
}
