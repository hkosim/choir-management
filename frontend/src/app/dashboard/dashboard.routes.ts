import { Routes } from '@angular/router';
import { Appointments } from './appointments/appointments';
import { resolveAppointments } from './appointments/resolver/appointment.resolver';

export const dashboardRoutes: Routes = [
  {
    path: 'appointments',
    component: Appointments,
    runGuardsAndResolvers: 'always',
    resolve: {
      appointmentPage: resolveAppointments,
    },
  },
];
