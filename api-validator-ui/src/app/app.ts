import { Component, inject } from '@angular/core';
import { RouterOutlet, RouterLink, Router, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  private router = inject(Router);
  private authService = inject(AuthService);

  // Muestra la barra de navegación solo si no estamos en el login
  showNavbar(): boolean {
    return this.router.url !== '/login' && this.router.url !== '/';
  }

  logout() {
    this.authService.logout(); // Limpia el sessionStorage
    this.router.navigate(['/login']);
  }
}