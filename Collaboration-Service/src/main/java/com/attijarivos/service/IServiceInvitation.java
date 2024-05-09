package com.attijarivos.service;

import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RededicationDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;

public interface IServiceInvitation <RequestDTO,ResquestUpdateDTO,ResponseDTO,ID> extends  IService <RequestDTO,ResquestUpdateDTO,ResponseDTO,ID>{
    List<ResponseDTO> createInvitationList(List<RequestDTO> requestDTOList) throws NotFoundDataException, RequiredDataException, NotValidDataException, RededicationDataException;
}
