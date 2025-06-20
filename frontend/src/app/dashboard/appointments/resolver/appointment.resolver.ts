import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { AppointmentService } from '../service/appointment.service';
import { Rehearsal } from '../model/rehearsal.model';
import { Performance } from '../model/performance.model';
import { AppointmentType } from '../model/appointment-type.model';

export async function resolveAppointment(
  activatedRouteSnapshot: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
): Promise<Rehearsal | Performance> {
  const appointmentService = inject(AppointmentService);

  const rawType = activatedRouteSnapshot.paramMap.get('type')?.toUpperCase();
  const id = activatedRouteSnapshot.paramMap.get('id');

  const isValidType =
    rawType &&
    Object.values(AppointmentType).includes(rawType as AppointmentType);

  if (!rawType || !isValidType) {
    throw new Error('Invalid appointment type');
  }

  if (!id) {
    throw new Error('Missing appointment ID');
  }

  const type = rawType as AppointmentType;

  try {
    const appointment$ = appointmentService.getAppointment(type, id);
    return await lastValueFrom(appointment$);
  } catch (error) {
    throw new Error((error as Error).message || 'Failed to load appointment');
  }
}
