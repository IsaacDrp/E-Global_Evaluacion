import { Component, ChangeDetectorRef } from '@angular/core'; // 1. Importar ChangeDetectorRef
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TransactionService } from '../../services/transaction.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './transaction-form.component.html'
})
export class TransactionFormComponent {
  transactionForm: FormGroup;
  resultado: any = null;
  errorCatUrl: string | null = null;
  loading: boolean = false; // Plus: Estado de carga

  constructor(
    private fb: FormBuilder, 
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef // 2. Inyectar en el constructor
  ) {
    this.transactionForm = this.fb.group({
      operacion: ['venta', [Validators.required, Validators.pattern('^(venta|cancelacion)$')]],
      importe: ['100.00', [Validators.required, Validators.pattern('^[0-9]+\\.[0-9]{2}$')]],
      cliente: ['Isaac', [Validators.required, Validators.minLength(3)]]
    });
  }

  onSubmit() {
    if (this.transactionForm.valid) {
      this.loading = true;
      this.resultado = null;
      this.errorCatUrl = null;

      const raw = this.transactionForm.value;
      const formattedRequest = {
        ...raw,
        importe: Number(raw.importe).toFixed(2)
      };

      this.transactionService.procesarVenta(formattedRequest).subscribe({
        next: (res) => {
          this.resultado = res;
          this.loading = false;
          // 3. Forzar actualización inmediata
          this.cdr.detectChanges(); 
        },
        error: (err) => {
          this.loading = false;
          const statusCode = err.status || 500;
          this.errorCatUrl = `https://http.cat/${statusCode}`;
          // 4. Forzar actualización inmediata también en error
          this.cdr.detectChanges(); 
        }
      });
    }
  }

  resetForm() {
    this.resultado = null;
    this.errorCatUrl = null;
    this.transactionForm.reset({ operacion: 'venta', importe: '100.00', cliente: 'Isaac' });
    this.cdr.detectChanges();
  }
}