package com.gonet.api_persistence.controller;

import com.gonet.api_persistence.model.dto.TransactionRequestDTO;
import com.gonet.api_persistence.model.dto.TransactionResponseDTO;
import com.gonet.api_persistence.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persistence")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // POST: Guardar transacción (Consumido por el API Gateway)
    @PostMapping("/save")
    public ResponseEntity<TransactionResponseDTO> save(@RequestBody TransactionRequestDTO request) {
        return new ResponseEntity<>(transactionService.saveTransaction(request), HttpStatus.CREATED);
    }

    // GET: Obtener todos los registros
    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponseDTO>> getAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    // GET: Buscar por referencia (Uso de @Query interno)
    @GetMapping("/reference/{ref}")
    public ResponseEntity<TransactionResponseDTO> getByReference(@PathVariable String ref) {
        return ResponseEntity.ok(transactionService.findByReferencia(ref));
    }

    // PATCH: Cancelar transacción
    // Recibe id y referencia
    @PatchMapping("/cancel")
    public ResponseEntity<TransactionResponseDTO> cancel(@RequestBody TransactionResponseDTO request) {
        Long id = Long.parseLong(request.getId());
        String ref = request.getReferencia();
        return ResponseEntity.ok(transactionService.cancelTransaction(id, ref));
    }
}