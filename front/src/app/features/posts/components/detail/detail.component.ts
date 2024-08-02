import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public post$: Observable<Post> | undefined;
  public postId: number | null;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private sessionService: SessionService
  ) {
    this.postId = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.post$ = this.postService.detail(this.postId);
  }

  ngOnInit(): void {
  }

}
