import { Component, inject, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterOutlet,
    RouterLink,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatListModule,
    CommonModule,
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private breakpointObserver = inject(BreakpointObserver);
  userRole: string = '';
  username: string = '';

  drawerMode: 'over' | 'side' = 'side';
  drawerOpened: boolean = true;

  ngOnInit(): void {
    this.userRole = this.authService.roles.includes('ROLE_ADMIN')
      ? 'admin'
      : 'user';

    this.username = this.authService.getUsername();

    this.breakpointObserver
      .observe([Breakpoints.Handset, Breakpoints.Tablet])
      .subscribe((result) => {
        this.drawerMode = result.matches ? 'over' : 'side';
      });
    if (this.drawerMode === 'over') {
      this.drawerOpened = false;
    }
  }

  navigateTo(route: string) {
    this.router.navigate(['dashboard', route]);
  }

  logout() {
    this.authService.logout();
  }
}
