package com.attijarivos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name="participation")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idParticipation;

    @ManyToOne
    @JoinColumn(name = "id_collaboration", referencedColumnName = "idCollaboration")
    private Collaboration collaboration;

    @Column(name = "id_membre",nullable = false,length = 30)
    private String idParticipant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateParticiaption;
}
