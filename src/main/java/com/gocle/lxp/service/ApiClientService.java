package com.gocle.lxp.service;

import com.gocle.lxp.dto.apikey.ApiClientCreateRequest;
import com.gocle.lxp.dto.apikey.ApiClientResponse;
import com.gocle.lxp.mapper.ApiClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiClientService {

    private final ApiClientMapper mapper;

    public List<ApiClientResponse> getClients() {
        return mapper.selectClientList();
    }

    public void create(ApiClientCreateRequest req) {
        mapper.insertClient(req);
    }

    public void update(Long clientId, ApiClientCreateRequest req) {
        mapper.updateClient(clientId, req);
    }

    public void updateCode(Long clientId, ApiClientCreateRequest req) {
        mapper.updateClientCode(clientId, req);
    }
    
    public void changeStatus(Long clientId, String status) {
        mapper.updateClientStatus(clientId, status);
    }
    
    public ApiClientResponse getClient(Long clientId) {
        return mapper.selectClientById(clientId);
    }
}
