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
    return this.http.get<User>(API_URL + userId);
};

getUserByEmail(email:string): Observable<User> {
  return this.http.get<User>(API_URL + 'email/'+email);
};

registerPatientInfo(patient:any): Observable<User> {
  console.log(patient);
  return this.http.post<User>(API_URL + 'patient', {
    email: patient.email,
    firstName: patient.firstName,
    lastName: patient.lastName,
    nhsNumber: patient.nhsNumber,
    birthdate: patient.birthdate,
    phone: patient.phone,
    address: patient.address,
    postCode: patient.postCode,
    city: patient.city
  });
};

}
