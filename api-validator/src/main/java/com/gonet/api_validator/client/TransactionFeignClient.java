package com.gonet.api_validator.client;

import com.gonet.api_validator.config.FeignConfig;
import com.gonet.api_validator.model.dto.TransactionRequestDTO;
import com.gonet.api_validator.model.dto.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "persistence-client",
        url = "${services.api-persistence.url}/api/v1/persistence",
        configuration = FeignConfig.class // <--- Esto es vital
)
public interface TransactionFeignClient {

    @PostMapping("/save")
    Object saveTransaction(@RequestBody TransactionRequestDTO request);

    @PatchMapping("/cancel")
    Object cancelTransaction(@RequestBody Map<String, Object> cancelData);

    @GetMapping("/all")
    List<TransactionResponseDTO> findAllTransactions();
}