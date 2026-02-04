package com.gonet.api_validator.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @NotBlank(message = "La operación es obligatoria")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "La operación solo debe contener caracteres")
    private String operacion; // [cite: 6, 12]

    @NotBlank(message = "El importe es obligatorio")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "El importe debe ser un formato de moneda válido (ej: 100.00)")
    private String importe; // [cite: 7, 12]

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre del cliente solo debe contener caracteres")
    private String cliente; // [cite: 8, 12]

    @NotBlank(message = "La firma es obligatoria")
    private String firma; // [cite: 9, 13]
}