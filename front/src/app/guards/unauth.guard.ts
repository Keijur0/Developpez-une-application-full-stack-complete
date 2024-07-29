import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class UnauthGuard implements CanActivate {

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  public canActivate() {
    if(this.sessionService.isLoggedIn) {
      this.router.navigate(['/post']);
    }
    return true;
  }
}
