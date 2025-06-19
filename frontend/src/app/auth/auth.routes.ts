import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Register } from './register/register';
import { Auth } from './auth';

export const authRoutes: Routes = [
  {
    path: 'register',
    component: Register,
    title: 'Register - CMA',
  },
  {
    path: 'login',
    component: Login,
    title: 'Login - CMA',
  },
];
