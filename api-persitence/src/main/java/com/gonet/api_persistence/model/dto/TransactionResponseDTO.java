package com.gonet.api_persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private String id; // ID es la PK del registro
    private String estatus; // ej "Aprobada"
    private String referencia; // Referencia Numérica aleatoria de 6 dígitos
    private String operacion;
}