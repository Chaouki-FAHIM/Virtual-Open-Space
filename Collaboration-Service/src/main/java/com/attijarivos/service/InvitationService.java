package com.attijarivos.service;

import com.attijarivos.DTO.InvitationRequest;
import com.attijarivos.DTO.InvitationResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.InvitationMapper;
import com.attijarivos.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service("service-layer-invitation")
@RequiredArgsConstructor
@Slf4j
public class InvitationService implements IService<InvitationRequest, InvitationResponse,Long> {

    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final WebClient webClient;

    @Override
    public InvitationResponse create(InvitationRequest invitationRequest) throws RequiredDataException, NotValidDataException {
        return null;
    }

    @Override
    public List<InvitationResponse> getAll() {
        return List.of();
    }

    @Override
    public InvitationResponse getOne(Long aLong) throws NotFoundDataException {
        return null;
    }

    @Override
    public InvitationResponse update(Long aLong, InvitationRequest invitationRequest) throws NotFoundDataException, RequiredDataException, NotValidDataException {
        return null;
    }

    @Override
    public void delete(Long aLong) throws NotFoundDataException, RequiredDataException, NotValidDataException {

    }
}
