import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthResponse } from '../features/auth/interfaces/responses/authResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLoggedIn = false;
  public authResponse: AuthResponse | undefined;

  private isLoggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn);

  public isLoggedIn$(): Observable<boolean> {
    return this.isLoggedInSubject.asObservable();
  }

  public logIn(authResponse: AuthResponse): void {
    this.authResponse = authResponse;
    this.isLoggedIn = true;
    this.updateSubject();
  }

  public logOut(): void {
    this.authResponse = undefined;
    this.isLoggedIn = false;
    this.updateSubject();
  }

  private updateSubject(): void {
    this.isLoggedInSubject.next(this.isLoggedIn);
  }

  constructor() { }
}
