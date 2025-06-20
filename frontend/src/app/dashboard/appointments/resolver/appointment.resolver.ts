import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { AppointmentService } from '../service/appointment.service';
import { Appointment } from '../model/appointment.model';

export async function resolveAppointment(
  activatedRouteSnapshot: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
): Promise<Appointment> {
  const appointmentService = inject(AppointmentService);
  const id = activatedRouteSnapshot.paramMap.get('id');

  if (!id) {
    throw new Error('Missing appointment ID');
  }

  try {
    const appointment$ = appointmentService.getAppointment(id);
    return await lastValueFrom(appointment$);
  } catch (error) {
    throw new Error((error as Error).message || 'Failed to load appointment');
  }
}
