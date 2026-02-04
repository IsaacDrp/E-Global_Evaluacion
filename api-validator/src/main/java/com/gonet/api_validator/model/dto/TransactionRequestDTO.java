package com.gonet.api_validator.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @NotBlank(message = "La operación es obligatoria")
    // Solo permitimos palabras clave específicas del negocio
    @Pattern(regexp = "^(venta|cancelacion)$", message = "Operación no permitida. Use 'venta' o 'cancelacion'")
    private String operacion;

    @NotBlank(message = "El importe es obligatorio")
    // Exactamente dos decimales para que el SHA-256 no falle
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "El importe debe tener formato exacto 0.00")
    private String importe;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,50}$", message = "Nombre inválido (3-50 caracteres)")
    private String cliente;

    @NotBlank(message = "La firma es obligatoria")
    private String firma;
}