import { Injectable, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, from, map } from 'rxjs';
import { User } from '../core/models/user.model';
import { Hospital } from '../core/models/hospital.model';
import { SpecialtyGroup } from '../core/models/specialty-group.model';
import { Specialty } from '../core/models/specialty';

const API_URL = '/api/users/';
const API_LOC_URL = '/api/hospitals/';

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

getNearestHospital(search:any): Observable<Hospital[]>{
  return this.http.post<Hospital[]>(API_LOC_URL+ 'getNearest',{
    longitude: search.longitude,
    latitude: search.latitude,
    specialityId: search.inputSpecialty
  });
}

bookBed(hospitalId:number): Observable<Hospital>{
  return this.http.post<Hospital>(API_LOC_URL+'bookBed/'+hospitalId, "");
}

registerPatientInfo(patient:any): Observable<User> {
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

getAllSpecialityGroups(): Observable<SpecialtyGroup[]>{
  return this.http.get<SpecialtyGroup[]>(API_LOC_URL+ 'speciality-groups');
}

getSpecialitiesBySpecialityGroupByName(value:string): Observable<Specialty[]>{
  return this.http.post<Specialty[]>(API_LOC_URL+ 'specialities',{
    name: value
  });
}

getSpecialitiesBySpecialityGroupById(value:number): Observable<Specialty[]>{
  return this.http.post<Specialty[]>(API_LOC_URL+ 'specialities',{
    id: value
  });
}


getPerimeter(){
  return this.http.get(API_LOC_URL+ "perimeter");
}

}
