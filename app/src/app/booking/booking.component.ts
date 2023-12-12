import { Component, Input } from '@angular/core';
import { User } from '../core/models/user.model';
import { Hospital } from '../core/models/hospital.model';
import { Observable, Subject, take, takeUntil } from 'rxjs';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { HttpClient } from '@angular/common/http';
import { SpecialtyGroup } from '../core/models/specialty-group.model';
import { Specialty } from '../core/models/specialty';

const API_LOC_URL = '/api/hospitals/';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})

export class BookingComponent {
  form: any = {};
  errorMessage = ''; 
  currentUser: any;
  @Input() user!: User; 
  @Input() hospital!: Hospital;  
  patient!: any; 
  registeredUser!: any;
  patientFullAddress!: string;
  private readonly unsubscribe$ = new Subject();
  hospitals$!: Hospital[];  
  speGroups$!: SpecialtyGroup[];    
  specialties$!: Specialty[];  
  speGroup!:any;
  isSuccessful = false;
  isLoggedIn = false;
  perimeter!: any;

  private readonly unsub$ = new Subject();
  
  constructor(private userService: UserService, private token: TokenStorageService, private http: HttpClient) { }

  ngOnInit() {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
      this.registeredUser =this.userService.getUserByEmail(this.currentUser.email).pipe(
        takeUntil(this.unsubscribe$)).subscribe(
          (data) => {          
        this.registeredUser = data;      
        this.userService.getUserContent(this.registeredUser.id).subscribe(
          (data) => {
            this.patient = data;
            this.patientFullAddress = this.patient.address+', '+this.patient.postCode+', '+this.patient.city;
          }
        );
          }
        );
    }

    this.userService.getAllSpecialityGroups().pipe(
      takeUntil(this.unsub$)).subscribe(
      (data)=>{
        this.speGroups$ = data;        
        this.userService.getSpecialitiesBySpecialityGroupByName(this.speGroups$[0].name).subscribe(
      data => {
        this.specialties$ = data;        
        let speGroupSelectValue = document.getElementById("inputSpecialtyGroup")?.nodeValue;
        let speSelect = document.getElementById("inputSpecialty");
        let speSelectLabel = document.getElementById("inputSpecialtyLabel");
        if(speGroupSelectValue==null){
          speSelect?.setAttribute("style", "visibility:hidden");
          speSelectLabel?.setAttribute("style", "visibility:hidden");          
        }
      }
    );
  }
      );
}


  onChange(e:Event){
    const target = e.target as HTMLSelectElement;
  if (target) {
    this.form = {inputSpecialtyGroup : target.value};  
  }
    this.userService.getSpecialitiesBySpecialityGroupById(parseInt(target.value)).subscribe(
      data => {
        this.specialties$ = data;
        let speSelect = document.getElementById("inputSpecialty");
        let speSelectLabel = document.getElementById("inputSpecialtyLabel");
        if(target.value==null || this.specialties$[0] === undefined){
          speSelect?.setAttribute("style", "visibility:hidden");
          speSelectLabel?.setAttribute("style", "visibility:hidden");
        }else{
          speSelect?.setAttribute("style", "visibility:visible");         
          speSelect?.setAttribute("style", "visibility:visible");          
          this.form = {inputSpecialty : this.specialties$[0].id};
        }
      }
    )
  }

  onSubmit() {
    console.log("form submitted");
    this.form.patientFullAddress = this.patientFullAddress;
    this.form.latitude = this.patient.latitude;
    this.form.longitude = this.patient.longitude;  
    this.userService.getPerimeter().subscribe(
      data=>{
        this.perimeter = data;        
        this.perimeter = this.perimeter/1000;
      }
    );
    this.userService.getNearestHospital(this.form).subscribe(
      data => {
        this.hospitals$ = data;
      }, err => {
        this.errorMessage = err.error.message;
        this.isSuccessful = false;
      }
    );
    this.isSuccessful= true;
  }
}
