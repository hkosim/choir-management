import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { AppointmentService } from '../service/appointment.service';
import { Rehearsal } from '../model/rehearsal.model';
import { Performance } from '../model/performance.model';

export async function resolveAppointment(
  activatedRouteSnapshot: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
): Promise<Rehearsal | Performance> {
  const appointmentService = inject(AppointmentService);

  const rawType = activatedRouteSnapshot.paramMap.get('type');

  if (rawType !== 'rehearsal' && rawType !== 'performance') {
    throw new Error('Invalid appointment type');
  }

  const type = rawType as 'rehearsal' | 'performance';
  const id = activatedRouteSnapshot.paramMap.get('id');

  if (type !== null && id !== null) {
    const appointment$ = appointmentService.getAppointment(type!, id!);
    const appointment = await lastValueFrom(appointment$).catch(
      async (err: Error) => {
        throw new Error(err.message);
      }
    );
    return appointment!;
  }
  throw new Error('Error: Performance not found!');
}
