import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationEnd, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { SessionInfo } from './interfaces/sessionInfo.interface';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  public hideToolbar: boolean = false;

  @ViewChild('sidenav') sidenav: MatSidenav | undefined;

  constructor(
    private sessionService: SessionService,
    private authService: AuthService,
    private router: Router
  ) {}

  public ngOnInit(): void {
    this.autoLog();
    this.router.events.subscribe(event => {
      if(event instanceof NavigationEnd) {
        this.isToolbarVisible();
      }
    });
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

  private isToolbarVisible(): void {
    const currentRoute = this.router.url;
    const isMobile = window.innerWidth <= 600;
    const isHomepage = currentRoute === '/';

    this.hideToolbar = (isMobile && (currentRoute.includes('auth/login') || currentRoute.includes('auth/register') || isHomepage)) || (!isMobile && isHomepage);
  }

  public closeSidenav() {
    this.sidenav?.close();
  }

}
