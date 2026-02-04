package com.gonet.api_validator.service;

import com.gonet.api_validator.model.dto.TransactionRequestDTO;

import java.util.Map;

public interface ValidationService {
    Object validateAndForward(TransactionRequestDTO request);
    Object forwardCancel(Map<String, Object> request);
}