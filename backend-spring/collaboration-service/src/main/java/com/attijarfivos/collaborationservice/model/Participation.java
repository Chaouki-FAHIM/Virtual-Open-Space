package com.attijarfivos.collaborationservice.model;

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
    private Long id;
    private Long membre;
    private Long collaboration;
    private Date date;
}
