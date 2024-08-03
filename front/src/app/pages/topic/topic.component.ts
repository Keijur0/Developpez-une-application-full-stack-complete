import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { map, Observable, of } from "rxjs";
import { Topic } from "src/app/interfaces/topic.interface";
import { User } from "src/app/interfaces/user.interface";
import { SessionService } from "src/app/services/session.service";
import { TopicService } from "src/app/services/topic.service";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent implements OnInit {

  public user: User | undefined;
  public userId: number;
  public topics$: Observable<Topic[]>;
  public subscribedTopics$: Observable<Topic[]> = of([]);

  constructor(
    private topicService: TopicService,
    private sessionService: SessionService,
    private userService: UserService,
    private matSnackBar: MatSnackBar
  ) {
    this.userId = this.sessionService.sessionInfo!.id;
    this.topics$ = this.topicService.getTopics();
    this.subscribedTopics$ = this.userService.subscriptions$;
  }

  public ngOnInit(): void {
    this.fetchSubscriptions();
  }

  public isSubscribed$(topicId: number): Observable<boolean> {
    return this.subscribedTopics$.pipe(
      map(topics => topics.some(topic => topic.id === topicId))
    );
  }

  public subscribe(topicId: number): void {
    this.userService.subscribe(this.userId, topicId).subscribe({
      next: _ => {
        this.matSnackBar.open('Abonnement réussi', 'Fermer', { duration: 3000 });
        this.fetchSubscriptions();
      }
    });
  }

  private fetchSubscriptions(): void {
    this.userService.getSubscriptions(this.userId).subscribe();
  }
}
