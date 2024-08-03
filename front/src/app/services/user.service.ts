import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { SessionInfo } from '../interfaces/sessionInfo.interface';
import { Topic } from '../interfaces/topic.interface';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'api/user';
  private subscriptionsSubject = new BehaviorSubject<Topic[]>([]);
  public subscriptions$ = this.subscriptionsSubject.asObservable();

  constructor(private httpClient: HttpClient) { }

  public getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.apiUrl}/${id}`);
  }

  public update(id: number, user: User): Observable<SessionInfo> {
    return this.httpClient.put<SessionInfo>(`${this.apiUrl}/${id}`, user);
  }

  public getSubscriptions(id: number): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.apiUrl}/${id}/subscriptions`).pipe(
      tap(topics => this.subscriptionsSubject.next(topics))
    );
  }

  public subscribe(id: number, topicId: number): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl}/${id}/subscribe/${topicId}`, null);
  }

  public unsubscribe(id: number, topicId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}/subscribe/${topicId}`);
  }

}
