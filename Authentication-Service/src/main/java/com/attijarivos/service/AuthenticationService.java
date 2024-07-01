package com.attijarivos.service;

import com.attijarivos.dto.request.AccountRequest;
import com.attijarivos.dto.request.LoginRequest;
import com.attijarivos.dto.response.AccountResponse;
import com.attijarivos.exception.RededicationAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    public Map<String, Object> login(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", loginRequest.getUsername());
        body.add("password", loginRequest.getPassword());

        log.debug("URL for sending login request: {}", url);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public AccountResponse register(AccountRequest accountRequest) throws RededicationAccountException {
        log.debug("Start registering account {}", accountRequest);
//        Set<Account> accounts = accountRepository.findByUsernameIgnoreCase(accountRequest.getUsername());
//
//        if (!accounts.isEmpty()) {
//            log.error("Account Data {} already consumed", accountRequest);
//            throw new RededicationAccountException();
//        }
//
//        // Encode the password before saving
//        String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());
//        accountRequest.setPassword(encodedPassword);
//
//        log.debug("Valid Data for registering a new account {}", accountRequest);
//
//        Account account = accountRepository.save(accountMapper.reqToModel(accountRequest));
//
//        log.debug("End registering account {}", account);
//
//        return accountMapper.modelToRes(account);
        return null;
    }
}
