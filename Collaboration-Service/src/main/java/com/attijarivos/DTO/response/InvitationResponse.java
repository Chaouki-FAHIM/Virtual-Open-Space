package com.attijarivos.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvitationResponse {
    private Long idInvitation;
    private String idInvite;
    private Date dateCreationInvitation;
    private Date dateParticiaption;
    private CollaborationResponse collaboration;
}

