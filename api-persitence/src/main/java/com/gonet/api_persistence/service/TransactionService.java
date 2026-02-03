package com.gonet.api_persistence.service;

import com.gonet.api_persistence.model.dto.TransactionRequestDTO;
import com.gonet.api_persistence.model.dto.TransactionResponseDTO;
import java.util.List;

public interface TransactionService {

    // Procesa y guarda la transacción generando la referencia de 6 dígitos
    TransactionResponseDTO saveTransaction(TransactionRequestDTO request);

    // Obtiene todos los registros
    List<TransactionResponseDTO> findAll();

    // Obtiene por referencia
    TransactionResponseDTO findByReferencia(String referencia);

    // Cancela la transacción (Cambio de estatus)
    TransactionResponseDTO cancelTransaction(Long id, String referencia);
}