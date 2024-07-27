import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { Posts } from '../../interfaces/posts.interface';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  private userId = this.sessionService.sessionInfo?.id;
  public posts$: Observable<Posts> = this.postService.subscribedPosts(this.userId);

  constructor(
    private postService: PostService,
    private sessionService: SessionService
  ) { }
}
