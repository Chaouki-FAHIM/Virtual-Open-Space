package com.attijarivos.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvitationListRequest {
    private Long idCollaboration;
    private List<String> idInvites;

    public void addInvite(String guest) {
        if (idInvites == null) {
            idInvites = new ArrayList<>();
        }
        idInvites.add(guest);
    }
}
