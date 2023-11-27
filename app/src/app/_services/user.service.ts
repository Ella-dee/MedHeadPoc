import { Injectable, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, from, map } from 'rxjs';
import { User } from '../core/models/user.model';

const API_URL = 'http://localhost:9004/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
 
  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<User[]> {
    return this.http.get<User[]>(API_URL + 'all');
  }

  getUserContent(userId:number): Observable<User> {
    console.log(API_URL + userId);
    return this.http.get<User>(API_URL + userId);
};

}
