import { Routes } from '@angular/router';
import { Home } from './home/home';
import { authRoutes } from './auth/auth.routes';
import { Auth } from './auth/auth';
import { Dashboard } from './dashboard/dashboard';
import { dashboardRoutes } from './dashboard/dashboard.routes';
import { AuthGuard } from './auth/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: Home,
    // canActivate: guards or something
  },
  {
    path: 'auth',
    component: Auth,
    children: authRoutes,
  },
  {
    path: 'dashboard',
    component: Dashboard,
    children: dashboardRoutes,
    canActivate: [AuthGuard],
  },
];
