package com.gonet.api_persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDTO {
    private String operacion;
    private BigDecimal importe;
    private String cliente;
    private String secreto; // este campo corresponde a la firma
}