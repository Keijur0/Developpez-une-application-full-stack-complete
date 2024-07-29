import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'api/user';

  private subscriptionsSubject = new BehaviorSubject<number[]>([]);
  public subscriptions$: Observable<number[]> = this.subscriptionsSubject.asObservable();

  constructor(private httpClient: HttpClient) { }

  public getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.apiUrl}/${id}`).pipe(
      tap(user => this.subscriptionsSubject.next(user.subscriptionsId))
    );
  }

  public update(id: number, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.apiUrl}/${id}`, user);
  }

  public subscribe(id: number, topicId: number): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl}/${id}/subscribe/${topicId}`, null).pipe(
      tap(() => {
        const currentSubscriptions = this.subscriptionsSubject.value;
        this.subscriptionsSubject.next([...currentSubscriptions, topicId]);
      })
    );
  }

  public unsubscribe(id: number, topicId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}/subscribe/${topicId}`).pipe(
      tap(() => {
        const currentSubscriptions = this.subscriptionsSubject.value;
        const updatedSubscriptions = currentSubscriptions.filter(tId => tId !== topicId );
        this.subscriptionsSubject.next(updatedSubscriptions);
      })
    )
  }

}
