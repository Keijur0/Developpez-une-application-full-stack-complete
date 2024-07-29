import { Component, OnInit } from "@angular/core";
import { map, Observable } from "rxjs";
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
  public subscriptions$: Observable<number[]>;

  constructor(
    private topicService: TopicService,
    private sessionService: SessionService,
    private userService: UserService
  ) {
    this.userId = this.sessionService.sessionInfo!.id;
    this.topics$ = this.topicService.getTopics();
    this.subscriptions$ = this.userService.subscriptions$;
  }

  public ngOnInit(): void {
    this.fetchUser();
  }

  private fetchUser(): void {
    this.userService.getUser(this.userId).subscribe((user: User) => {
      this.user = user;
    });
  }

  public isSubscribed$(topicId: number): Observable<boolean> {
    return this.subscriptions$.pipe(
      map((subscriptions: number[]) => subscriptions.includes(topicId))
    );
  }

  public subscribe(topicId: number): void {
    this.userService.subscribe(this.userId, topicId).subscribe(_ => this.fetchUser());
  }

}
