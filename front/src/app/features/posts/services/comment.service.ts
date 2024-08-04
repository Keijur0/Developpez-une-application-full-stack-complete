import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  private apiUrl = 'api/comment'

  public getComments(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.apiUrl}/${postId}`);
  }

  public createComment(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.apiUrl, comment);
  }
}
