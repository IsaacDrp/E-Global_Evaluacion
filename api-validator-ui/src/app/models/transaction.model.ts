export interface TransactionRequest {
  operacion: string;
  importe: string;
  cliente: string;
  firma?: string;
}

export interface TransactionResponse {
  id: string;
  referencia: string;
  estatus: string;
  operacion: string;
}