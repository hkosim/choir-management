import { Routes } from '@angular/router';
import { AppointmentsList } from './components/appointments-list/appointments-list';
import { resolveAppointmentAttendancePage } from './resolver/appointment-attendance-page.resolver';
import { EditAppointment } from './components/edit-appointment/edit-appointment';
import { AdminGuard } from '../../auth/guards/admin.guard';
import { resolveAppointment } from './resolver/appointment.resolver';
import { AddAppointment } from './components/add-appointment/add-appointment';

export const appointmentRoutes: Routes = [
  {
    path: 'list',
    title: 'Appointments - CMA',
    component: AppointmentsList,
    runGuardsAndResolvers: 'always',
    resolve: {
      appointmentAttendancePage: resolveAppointmentAttendancePage,
    },
  },
  {
    path: 'add',
    title: 'Add new Appointment - CMA',
    component: AddAppointment,
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
  },
  {
    path: 'edit/:type/:id',
    title: 'Edit - CMA',
    component: EditAppointment,
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
    resolve: {
      appointment: resolveAppointment,
    },
  },
  { path: '**', redirectTo: 'list' },
];
