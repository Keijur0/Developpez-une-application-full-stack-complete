import { Component } from "@angular/core";
import { Observable } from "rxjs";
import { Topic } from "src/app/interfaces/topic.interface";
import { SessionService } from "src/app/services/session.service";
import { TopicService } from "src/app/services/topic.service";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent {

  constructor(
    private topicService: TopicService,
    private sessionService: SessionService,
    private userService: UserService
  ) {}

  public topics$: Observable<Topic[]> = this.topicService.getTopics();

  public subscribe() {


  }

}
