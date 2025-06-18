import { Routes } from '@angular/router';
import { Appointments } from './appointments/appointments';
import { AdminGuard } from '../auth/guards/admin.guard';
import { EditAppointment } from './appointments/components/edit-appointment/edit-appointment';
import { resolveAppointments } from './appointments/resolver/appointments.resolver';

export const dashboardRoutes: Routes = [
  {
    path: 'appointments',
    component: Appointments,
    runGuardsAndResolvers: 'always',
    resolve: {
      appointmentPage: resolveAppointments,
    },
  },
  {
    path: 'appointments/edit/:id',
    component: EditAppointment,
    runGuardsAndResolvers: 'always',
    canActivate:[AdminGuard],
  },
  
];
