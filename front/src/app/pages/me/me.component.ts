import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { combineLatest, map, Observable } from 'rxjs';
import { Topic } from 'src/app/interfaces/topic.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;
  public userId: number;
  public subscriptions$: Observable<number[]>;
  public topics$: Observable<Topic[]>;
  public subscribedTopics$: Observable<Topic[]>;

  constructor(
    private sessionService: SessionService,
    private userService: UserService,
    private topicService: TopicService,
    private router: Router,
  ) {
    this.userId = this.sessionService.sessionInfo!.id;
    this.subscriptions$ = this.userService.subscriptions$;
    this.topics$ = this.topicService.getTopics();
    this.subscribedTopics$ = combineLatest([this.topics$, this.subscriptions$]).pipe(
      map(([topics, subscriptions]) =>
      topics.filter((topic: Topic) => subscriptions.includes(topic.id)))
    );
  }

  public ngOnInit(): void {
    this.fetchUser();
    }
    
  private fetchUser() {
    this.userService.getUser(this.userId).subscribe(user => {
      this.user = user;
    });
  }

  private logout() {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  public unsubscribe(topicId: number) {
    this.userService.unsubscribe(this.userId, topicId).subscribe();
  }

}
