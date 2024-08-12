import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
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

  @ViewChild(FormGroupDirective) formDirective: FormGroupDirective | undefined;

  public post: Post | undefined;
  public postId: number;
  public comments$: Observable<Comment[]> | undefined;
  public userId: number;
  public commentForm: FormGroup | undefined = this.formBuider.group({
    message: ['', [Validators.required, Validators.maxLength(2000)]]
  });


  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private sessionService: SessionService,
    private commentService: CommentService,
    private formBuider: FormBuilder,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {
    this.postId = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.userId = this.sessionService.sessionInfo!.id;
  }

  ngOnInit(): void {
    this.fetchComments();
    this.postService.detail(this.postId).subscribe({
      next: (post: Post) => {
        this.post = post;
      },
      error: _ => this.router.navigate(['not-found'])
    })
  }

  public sendComment(): void {
    let comment = this.commentForm?.value as Comment;
    comment.userId = this.userId;
    comment.postId = this.postId;
    this.commentService.createComment(comment).subscribe({
      next: _ => {
        this.formDirective?.resetForm();
        this.matSnackBar.open('Commentaire envoyé avec succès', 'Fermer', { duration: 3000 });
        this.fetchComments();
      }
    })
  }

  private fetchComments(): void {
    this.comments$ = this.commentService.getComments(this.postId);
  }

}
