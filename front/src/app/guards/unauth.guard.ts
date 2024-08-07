import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class UnauthGuard  {

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  public canActivate() {
    if(this.sessionService.isLoggedIn) {
      this.router.navigate(['/posts']);
    }
    return true;
  }
}
