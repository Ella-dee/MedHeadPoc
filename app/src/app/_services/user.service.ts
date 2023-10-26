import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../core/models/user.model';

const API_URL = 'api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<User[]> {
    return this.http.get<User[]>(API_URL + 'all');
  }

  getUserContent(userId:number): Observable<User> {
    return this.http.get<User>(API_URL + userId);
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<User[]> {
    return this.http.get<User[]>(API_URL + 'all');
  }
}
