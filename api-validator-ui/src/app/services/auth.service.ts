import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) {}

  login(usuario: string, password: string) {
  sessionStorage.removeItem('token'); 
  
  return this.http.post(`${this.apiUrl}/login`, { usuario, password }, { responseType: 'text' })
    .pipe(
      tap(() => {
        const auth = btoa(`${usuario}:${password}`);
        sessionStorage.setItem('token', auth);
      })
    );
}

  getToken() {
    return sessionStorage.getItem('token');
  }

  logout() {
    sessionStorage.clear();
  }
}