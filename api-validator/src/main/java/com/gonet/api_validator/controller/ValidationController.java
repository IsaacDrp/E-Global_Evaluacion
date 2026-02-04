package com.gonet.api_validator.controller;

import com.gonet.api_validator.model.dto.TransactionRequestDTO;
import com.gonet.api_validator.model.dto.TransactionResponseDTO;
import com.gonet.api_validator.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/validator")
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping("/process")
    public Object process(@Valid @RequestBody TransactionRequestDTO request) {
        return validationService.validateAndForward(request);
    }
    @PatchMapping("/cancel")
    public Object cancel(@RequestBody Map<String, Object> request) {
        return validationService.forwardCancel(request);
    }

    @GetMapping("/all")
    public List<TransactionResponseDTO> findAll() {
        return validationService.getAll();
    }
}