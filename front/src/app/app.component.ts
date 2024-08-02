import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Observable } from 'rxjs';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public showToolbar: boolean = true;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
        this.showToolbar = event.url !== '/';
      });
  }

  public isLoggedIn$(): Observable<boolean> {
    return this.sessionService.isLoggedIn$();
  }

}
