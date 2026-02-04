import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../../services/transaction.service';
import { TransactionResponse } from '../../models/transaction.model';

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-list.component.html'
})
export class TransactionListComponent implements OnInit {
  transacciones: TransactionResponse[] = [];

  constructor(private transactionService: TransactionService) {}

  ngOnInit() {
    this.cargarTransacciones();
    this.cargarTransacciones();
  }

  cargarTransacciones() {
    this.transactionService.listarTodo().subscribe({
      next: (data) => {
        this.transacciones = data;
        console.log('Historial sincronizado');
      },
      error: (err) => console.error('Error al cargar la lista:', err)
    });
  }
}