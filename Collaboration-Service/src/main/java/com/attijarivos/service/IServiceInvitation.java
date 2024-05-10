package com.attijarivos.service;

import com.attijarivos.DTO.request.JoindreRequest;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RededicationDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;

public interface IServiceInvitation <RequestDTO,ResponseDTO,ID> extends  IService <RequestDTO, JoindreRequest,ResponseDTO,ID>{
    List<ResponseDTO> createInvitationList(List<RequestDTO> requestDTOList) throws NotFoundDataException, RequiredDataException, NotValidDataException, RededicationDataException;
}
