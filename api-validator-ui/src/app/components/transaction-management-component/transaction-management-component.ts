import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionService } from '../../services/transaction.service';
import { TransactionResponse } from '../../models/transaction.model';

@Component({
  selector: 'app-transaction-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-management-component.html',
  styleUrls: ['./transaction-management-component.scss']
})
export class TransactionManagementComponent {
  // Modelos para el data-binding (Ventana 3)
  transactionId: string = '';
  reference: string = '';
  
  // Estado de la respuesta
  resultado: TransactionResponse | null = null;
  loading: boolean = false;

  constructor(private transactionService: TransactionService) {}

  onCancel() {
    if (!this.transactionId || !this.reference) {
      alert('Por favor, ingresa tanto el ID como la Referencia');
      return;
    }

    this.loading = true;
    console.log(`Iniciando cancelación PATCH para ID: ${this.transactionId}`);

    this.transactionService.cancelarVenta(this.transactionId, this.reference).subscribe({
      next: (res) => {
        this.resultado = res;
        this.loading = false;
        alert('Estatus actualizado: Transacción Cancelada');
      },
      error: (err) => {
        this.loading = false;
        console.error('Error en PATCH:', err);
        alert('Error al intentar cancelar: ' + (err.error?.message || 'No se encontró la transacción'));
      }
    });
  }

  limpiar() {
    this.transactionId = '';
    this.reference = '';
    this.resultado = null;
  }
}