import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AppointmentService } from '../service/appointment.service';
import { lastValueFrom } from 'rxjs';

export async function resolveAppointments(
  activatedRouteSnapshot: ActivatedRouteSnapshot,
  routerState: RouterStateSnapshot
) {
  const appointmentService = inject(AppointmentService);
  const appointmentPage$ = appointmentService.getAppointments();
  return await lastValueFrom(appointmentPage$).catch(async (err: Error) => {
    console.log(err);
  });
}
