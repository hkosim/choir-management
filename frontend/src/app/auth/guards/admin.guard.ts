import { DestroyRef, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

import { lastValueFrom } from 'rxjs';
import { AuthService } from '../auth.service';

export async function AdminGuard(
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) {
  const authService = inject(AuthService);

  const username: string | null = sessionStorage.getItem('username');
  const roles: String[] = authService.roles;
  // Make sure that the user is authenticated
  if (roles.includes('ROLE_ADMIN')) {
    return true;
  }
  return false;
}
