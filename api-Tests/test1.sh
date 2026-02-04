#!/bin/bash

BASE_URL="http://localhost:8080/api/v1/validator/process"
AUTH_HEADER=$(echo -n "isaacdp:contra123" | base64)

echo "--- INICIANDO TEST DE VULNERABILIDAD DE VALIDACIONES ---"

# Función para ejecutar curl y mostrar el resultado simplificado
test_payload() {
    local descripcion=$1
    local json_data=$2
    echo -e "\nPrueba: $descripcion"
    local status=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL" \
         -H "Authorization: Basic $AUTH_HEADER" \
         -H "Content-Type: application/json" \
         -d "$json_data")
    
    if [[ "$status" == "400" ]]; then
        echo "RESULTADO: ✅ RECHAZADO CORRECTAMENTE (400)"
    else
        echo "RESULTADO: ❌ ERROR - El servidor aceptó el dato o respondió $status"
    fi
}

# 1. Operación inválida (No es 'venta' ni 'cancelacion')
test_payload "Operación inexistente (test_hack)" \
'{
  "operacion": "test_hack",
  "importe": "100.00",
  "cliente": "Isaac",
  "firma": "firma_cualquiera"
}'

# 2. Importe sin decimales (Rompe la consistencia del SHA)
test_payload "Importe sin decimales (100)" \
'{
  "operacion": "venta",
  "importe": "100",
  "cliente": "Isaac",
  "firma": "firma_cualquiera"
}'

# 3. Importe con más de 2 decimales
test_payload "Importe con 3 decimales (100.001)" \
'{
  "operacion": "venta",
  "importe": "100.001",
  "cliente": "Isaac",
  "firma": "firma_cualquiera"
}'

# 4. Cliente demasiado corto (Violación de minLength)
test_payload "Cliente muy corto (Is)" \
'{
  "operacion": "venta",
  "importe": "100.00",
  "cliente": "Is",
  "firma": "firma_cualquiera"
}'

# 5. Cliente con caracteres especiales (Inyección de símbolos)
test_payload "Cliente con símbolos (Isaac#$%)" \
'{
  "operacion": "venta",
  "importe": "100.00",
  "cliente": "Isaac#$%",
  "firma": "firma_cualquiera"
}'

# 6. Campos vacíos (Validación de @NotBlank)
test_payload "Campos vacíos" \
'{
  "operacion": "",
  "importe": "",
  "cliente": "",
  "firma": ""
}'

echo -e "\n--- FIN DE PRUEBAS DE VALIDACIÓN ---"
