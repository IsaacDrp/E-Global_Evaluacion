import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  // No interceptar el login para evitar bucles
  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Basic ${token}`
      }
    });
    console.log('--- CABECERA INYECTADA ---');
    return next(authReq);
  }

  return next(req);
};