import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { SessionInfo } from './interfaces/sessionInfo.interface';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  public showToolbar: boolean = true;

  constructor(
    private sessionService: SessionService,
    private authService: AuthService,
    private router: Router
  ) {
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
        this.showToolbar = event.url !== '/';
      });
  }
  public ngOnInit(): void {
    this.autoLog();
  }

  public isLoggedIn$(): Observable<boolean> {
    return this.sessionService.isLoggedIn$();
  }

  public autoLog(): void {
    this.authService.me().subscribe(
      (sessionInfo: SessionInfo) => {
        this.sessionService.logIn(sessionInfo);
      });
  }

}
