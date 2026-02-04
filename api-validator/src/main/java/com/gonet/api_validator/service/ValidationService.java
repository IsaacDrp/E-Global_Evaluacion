package com.gonet.api_validator.service;

import com.gonet.api_validator.model.dto.TransactionRequestDTO;
import com.gonet.api_validator.model.dto.TransactionResponseDTO;

import java.util.List;
import java.util.Map;

public interface ValidationService {
    Object validateAndForward(TransactionRequestDTO request);
    Object forwardCancel(Map<String, Object> request);
    List<TransactionResponseDTO> getAll();
}