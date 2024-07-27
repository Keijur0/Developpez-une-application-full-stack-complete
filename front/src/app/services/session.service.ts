import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInfo } from '../interfaces/sessionInfo.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLoggedIn = false;
  public sessionInfo: SessionInfo | undefined;

  private isLoggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn);

  public isLoggedIn$(): Observable<boolean> {
    return this.isLoggedInSubject.asObservable();
  }

  public logIn(sessionInfo: SessionInfo): void {
    this.sessionInfo = sessionInfo;
    this.isLoggedIn = true;
    this.updateSubject();
  }

  public logOut(): void {
    this.sessionInfo = undefined;
    this.isLoggedIn = false;
    this.updateSubject();
  }

  private updateSubject(): void {
    this.isLoggedInSubject.next(this.isLoggedIn);
  }
}
