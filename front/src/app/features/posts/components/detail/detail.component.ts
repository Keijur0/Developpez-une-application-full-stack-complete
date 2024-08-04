import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { Comment } from '../../interfaces/comment.interface';
import { Post } from '../../interfaces/post.interface';
import { CommentService } from '../../services/comment.service';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public post$: Observable<Post> | undefined;
  public postId: number;
  public comments$: Observable<Comment[]> | undefined;
  public userId: number;
  public commentForm: FormGroup = this.formBuider.group({
    message: ['', [Validators.required, Validators.maxLength(2000)]]
  });

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private sessionService: SessionService,
    private commentService: CommentService,
    private formBuider: FormBuilder,
    private matSnackBar: MatSnackBar
  ) {
    this.postId = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.userId = this.sessionService.sessionInfo!.id;
    this.post$ = this.postService.detail(this.postId);
  }

  ngOnInit(): void {
    this.fetchComments();
  }

  public sendComment(): void {
    console.log("send");
    let comment = this.commentForm.value as Comment;
    comment.userId = this.userId;
    comment.postId = this.postId;
    this.commentService.createComment(comment).subscribe({
      next: _ => {
        this.matSnackBar.open('Commentaire envoyé avec succès', 'Fermer', { duration: 3000 });
        this.fetchComments();
      }
    })
  }

  private fetchComments(): void {
    this.comments$ = this.commentService.getComments(this.postId);
  }

}
