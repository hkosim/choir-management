import { Routes } from '@angular/router';
import { AppointmentsList } from './components/appointments-list/appointments-list';
import { resolveAppointments } from './resolver/appointments.resolver';
import { EditAppointment } from './components/edit-appointment/edit-appointment';
import { AdminGuard } from '../../auth/guards/admin.guard';
import { resolveAppointment } from './resolver/appointment.resolver';

export const appointmentRoutes: Routes = [
  {
    path: 'list',
    component: AppointmentsList,
    runGuardsAndResolvers: 'always',
    resolve: {
      appointmentPage: resolveAppointments,
    },
  },
  {
    path: 'edit/:type/:id',
    component: EditAppointment,
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
    resolve: {
      appointment: resolveAppointment,
    },
  },
  { path: '**', redirectTo: 'list' },
];
