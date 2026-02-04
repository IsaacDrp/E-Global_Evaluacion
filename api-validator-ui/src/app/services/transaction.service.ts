import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TransactionRequest, TransactionResponse } from '../models/transaction.model';
import { AuthService } from './auth.service';
import * as CryptoJS from 'crypto-js';

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private apiUrl = 'http://localhost:8080/api/v1/validator';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders() {
    return new HttpHeaders({
      'Authorization': `Basic ${this.authService.getToken()}`
    });
  }

  procesarVenta(req: TransactionRequest) {
    // 1. Replicamos la lógica del Backend: concatenar sin espacios
    const dataToHash = req.operacion.trim() + req.importe.trim() + req.cliente.trim();
    
    // 2. Generamos el Hash SHA-256
    const hash = CryptoJS.SHA256(dataToHash).toString(CryptoJS.enc.Hex);
    
    // 3. Insertamos la firma en el objeto
    const payload = { ...req, firma: hash };

    return this.http.post<TransactionResponse>(`${this.apiUrl}/process`, payload, { headers: this.getHeaders() });
  }

  cancelarVenta(id: string, referencia: string) {
    return this.http.patch<TransactionResponse>(`${this.apiUrl}/cancel`, { id, referencia }, { headers: this.getHeaders() });
  }

  listarTodo() {
  return this.http.get<TransactionResponse[]>(`${this.apiUrl}/all`, { headers: this.getHeaders() });
}
}