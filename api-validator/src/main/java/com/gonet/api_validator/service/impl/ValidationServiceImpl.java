package com.gonet.api_validator.service.impl;

import com.gonet.api_validator.client.TransactionFeignClient;
import com.gonet.api_validator.model.dto.TransactionRequestDTO;
import com.gonet.api_validator.model.dto.TransactionResponseDTO;
import com.gonet.api_validator.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final TransactionFeignClient feignClient;

    @Override
    public Object validateAndForward(TransactionRequestDTO request) {
        // 1. Limpiamos espacios en blanco accidentales de los datos
        String op = request.getOperacion().trim();
        String imp = request.getImporte().trim();
        String cli = request.getCliente().trim();

        // 2. Re-construimos la cadena
        String dataToHash = op + imp + cli;
        System.out.println("DEBUG - Cadena final: [" + dataToHash + "]");

        // 3. Calculamos SHA-256
        String calculatedSignature = generateSHA256(dataToHash);
        System.out.println("DEBUG - Mi firma:    " + calculatedSignature);
        System.out.println("DEBUG - Tu firma:    " + request.getFirma().trim().toLowerCase());

        // 4. Comparación robusta
        if (!calculatedSignature.equalsIgnoreCase(request.getFirma().trim())) {
            throw new RuntimeException("Bad Request: Firma inválida.");
        }

        return feignClient.saveTransaction(request);
    }

    private String generateSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error calculando el hash SHA");
        }
    }

    public Object forwardCancel(Map<String, Object> request) {
        return feignClient.cancelTransaction(request);
    }

    @Override
    public List<TransactionResponseDTO> getAll() {
        try {
            System.out.println("Solicitando historial completo a la API de Persistencia...");
            return feignClient.findAllTransactions();
        } catch (Exception e) {
            System.err.println("Error al conectar con el microservicio de persistencia: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}