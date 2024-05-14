package com.attijarivos.service;

import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RededicationInvitationException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;

public interface IInvitationService<RequestDTO,ResponseDTO,ID> extends  IService <RequestDTO,ResponseDTO,ID>{
    List<ResponseDTO> createInvitationList(InvitationListRequest requestDTOList) throws NotFoundDataException, RequiredDataException, RededicationInvitationException;
}
