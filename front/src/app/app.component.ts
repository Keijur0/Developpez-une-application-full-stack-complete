import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private sessionService: SessionService) {
  }

  public isLoggedIn$(): Observable<boolean> {
    return this.sessionService.isLoggedIn$();
  }

}
