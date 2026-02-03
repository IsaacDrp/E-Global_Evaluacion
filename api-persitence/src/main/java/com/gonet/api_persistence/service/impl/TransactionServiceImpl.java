package com.gonet.api_persistence.service.impl;

import com.gonet.api_persistence.model.dto.TransactionRequestDTO;
import com.gonet.api_persistence.model.dto.TransactionResponseDTO;
import com.gonet.api_persistence.model.entity.TransactionEntity;
import com.gonet.api_persistence.repository.TransactionRepository;
import com.gonet.api_persistence.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public TransactionResponseDTO saveTransaction(TransactionRequestDTO request) {

        // 1. Generar referencia aleatoria de 6 dígitos
        String referenciaAleatoria = String.format("%06d", new Random().nextInt(1000000));

        // 2. Mapeo de DTO a Entidad
        TransactionEntity entity = TransactionEntity.builder()
                .operacion(request.getOperacion())
                .importe(request.getImporte())
                .cliente(request.getCliente())
                .referencia(referenciaAleatoria)
                .estatus("Aprobada") // Leyenda obligatoria
                .secreto(request.getSecreto())
                .build();

        // 3. Persistencia en H2
        TransactionEntity savedEntity = repository.save(entity);

        // 4. Mapeo de Entidad a Response DTO
        return TransactionResponseDTO.builder()
                .id(savedEntity.getId().toString())
                .estatus(savedEntity.getEstatus())
                .referencia(savedEntity.getReferencia())
                .operacion(savedEntity.getOperacion())
                .build();
    }

    @Override
    @Transactional
    public TransactionResponseDTO cancelTransaction(Long id, String referencia) {

        // 1. Ejecutar la actualización mediante la @Query definida en el repositorio
        int updatedRows = repository.updateEstatus(id, referencia, "cancelada");

        if (updatedRows == 0) {
            throw new RuntimeException("No se encontró la transacción con ID: " + id + " y referencia: " + referencia);
        }

        // 2. Buscamos el registro actualizado para retornar la respuesta correcta
        TransactionEntity updatedEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error al recuperar la transacción cancelada"));

        return TransactionResponseDTO.builder()
                .id(updatedEntity.getId().toString())
                .estatus(updatedEntity.getEstatus())
                .referencia(updatedEntity.getReferencia())
                .operacion(updatedEntity.getOperacion())
                .build();
    }

    @Override
    public List<TransactionResponseDTO> findAll() {
        return repository.findAll().stream().map(this::convertToDto).toList();
    }

    private TransactionResponseDTO convertToDto(TransactionEntity entity) {
        return TransactionResponseDTO.builder()
                .id(entity.getId().toString())
                .estatus(entity.getEstatus())
                .referencia(entity.getReferencia())
                .operacion(entity.getOperacion())
                .build();
    }

    @Override
    public TransactionResponseDTO findByReferencia(String referencia) {
        TransactionEntity entity = repository.findByReferencia(referencia)
                .orElseThrow(() -> new RuntimeException("Referencia no encontrada: " + referencia));

        return TransactionResponseDTO.builder()
                .id(entity.getId().toString())
                .estatus(entity.getEstatus())
                .referencia(entity.getReferencia())
                .operacion(entity.getOperacion())
                .build();
    }
}