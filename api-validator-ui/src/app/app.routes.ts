import { Routes } from '@angular/router';
import { LoginComponent } from './components/login.component/login.component';
import { TransactionFormComponent } from './components/transaction-form.component/transaction-form.component';
import { TransactionManagementComponent } from './components/transaction-management-component/transaction-management-component';
import { TransactionListComponent } from './components/transaction-list.component/transaction-list.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: TransactionFormComponent }, // Coincide con routerLink="/registro"
  { path: 'gestion', component: TransactionManagementComponent }, // Coincide con routerLink="/gestion"
  { path: 'listado', component: TransactionListComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];