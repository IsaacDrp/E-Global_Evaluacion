#!/bin/bash

# Configuración
BASE_URL="http://localhost:8080/api/v1"
USER="isaacdp"
PASS="contra123"
# Codificamos credenciales para Basic Auth
AUTH_HEADER=$(echo -n "$USER:$PASS" | base64)

echo "--- INICIANDO TEST DE INTEGRIDAD DE API ---"

# 1. TEST DE LOGIN
echo -e "\n[1/4] Probando Login..."
LOGIN_RES=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE_URL/auth/login" \
     -H "Content-Type: application/json" \
     -d "{\"usuario\":\"$USER\", \"password\":\"$PASS\"}")

if [ "$LOGIN_RES" == "200" ]; then
    echo "SUCCESS: Login correcto (200)"
else
    echo "ERROR: Login fallido ($LOGIN_RES)"
fi

# 2. TEST DE VENTA EXITOSA (Generando firma real)
echo -e "\n[2/4] Probando Registro de Venta (Con firma válida)..."
OPERACION="venta"
IMPORTE="150.50"
CLIENTE="Isaac"
# Generamos el hash SHA-256 de la cadena concatenada (igual que en Java/Angular)
DATA_TO_HASH="${OPERACION}${IMPORTE}${CLIENTE}"
FIRMA=$(echo -n "$DATA_TO_HASH" | openssl dgst -sha256 | sed 's/^.* //')

Venta_RES=$(curl -s -X POST "$BASE_URL/validator/process" \
     -H "Authorization: Basic $AUTH_HEADER" \
     -H "Content-Type: application/json" \
     -d "{
        \"operacion\": \"$OPERACION\",
        \"importe\": \"$IMPORTE\",
        \"cliente\": \"$CLIENTE\",
        \"firma\": \"$FIRMA\"
     }")

echo "Respuesta del Servidor: $Venta_RES"

# 3. TEST DE ERROR: FIRMA INVÁLIDA
echo -e "\n[3/4] Probando error de integridad (Firma alterada)..."
curl -s -o /dev/null -w "Status esperado: 400/500 | Status recibido: %{http_code}\n" -X POST "$BASE_URL/validator/process" \
     -H "Authorization: Basic $AUTH_HEADER" \
     -H "Content-Type: application/json" \
     -d "{
        \"operacion\": \"venta\",
        \"importe\": \"100.00\",
        \"cliente\": \"Isaac\",
        \"firma\": \"firma_falsa_123\"
     }"

# 4. TEST DE LISTADO (FIND ALL)
echo -e "\n[4/4] Consultando Historial Completo..."
curl -s -X GET "$BASE_URL/validator/all" \
     -H "Authorization: Basic $AUTH_HEADER" | jq . 2>/dev/null || curl -s -X GET "$BASE_URL/validator/all" -H "Authorization: Basic $AUTH_HEADER"

echo -e "\n--- TESTS FINALIZADOS ---"
