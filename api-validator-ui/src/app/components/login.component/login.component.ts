// ...existing code...
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-4 card p-4 shadow">
          <h2 class="text-center">Acceso</h2>
          <form [formGroup]="loginForm" (ngSubmit)="onLogin()">
            <div class="mb-3">
              <label>Usuario</label>
              <input type="text" formControlName="usuario" class="form-control">
            </div>
            <div class="mb-3">
              <label>Contraseña</label>
              <input type="password" formControlName="password" class="form-control">
            </div>
            <button class="btn btn-primary w-100" [disabled]="loginForm.invalid">Entrar</button>
          </form>
        </div>
      </div>
    </div>
  `
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onLogin() {
  if (this.loginForm.valid) {
    const { usuario, password } = this.loginForm.value;
    
    console.log('--- INTENTO DE ACCESO ---');
    
    this.auth.login(usuario, password).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        // Cambiamos '/transacciones' por '/registro' que es nuestra nueva ruta base
        this.router.navigate(['/registro']);
      },
      error: (err) => {
        console.error('Error en login:', err);
        alert('Credenciales incorrectas o servidor no disponible');
      }
    });
  }
}
}