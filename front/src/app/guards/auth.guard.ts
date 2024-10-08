import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard  {

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  public canActivate() {
    if(!this.sessionService.isLoggedIn) {
      this.router.navigate(['auth/login']);
    }
    return true;
  }
}
  
