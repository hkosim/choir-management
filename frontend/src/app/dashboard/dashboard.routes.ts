import { Routes } from '@angular/router';
import { appointmentRoutes } from './appointments/appointments.routes';

export const dashboardRoutes: Routes = [
  {
    path: 'appointments',
    runGuardsAndResolvers: 'always',
    children: appointmentRoutes,
    title: 'Appointments - CMA',
  },
];
