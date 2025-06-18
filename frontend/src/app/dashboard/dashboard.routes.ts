import { Routes } from '@angular/router';
import { AdminGuard } from '../auth/guards/admin.guard';
import { resolveAppointments } from './appointments/resolver/appointments.resolver';
import { appointmentRoutes } from './appointments/appointments.routes';

export const dashboardRoutes: Routes = [
  {
    path: 'appointments',
    runGuardsAndResolvers: 'always',
    children: appointmentRoutes,
    resolve: {
      appointmentPage: resolveAppointments,
    },
  },
];
