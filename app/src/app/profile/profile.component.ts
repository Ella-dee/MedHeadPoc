import { Component, OnInit, Input } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { User } from '../core/models/user.model';
import { UserService } from '../_services/user.service';
import { Observable, Subject, map, takeUntil, tap } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  @Input() user!: User;  
  patient!: any; 
  registeredUser!: any;
  private readonly unsubscribe$ = new Subject();
  form: any = {};
  isSuccessful = false;
  isLoggedIn = true;
  infoRegisteredFailed = false;
  errorMessage = '';

  constructor(private token: TokenStorageService, private userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    this.isLoggedIn = true;     
    this.registeredUser =this.userService.getUserByEmail(this.currentUser.email).pipe(
      takeUntil(this.unsubscribe$)).subscribe(
        (data) => {          
      this.registeredUser = data; 
      this.userService.getUserContent(this.registeredUser.id).subscribe(
        (data) => {
          this.patient = data;
        }
      );
        }
      ); 
  }

  onSubmit() {
    console.log("form submitted");
    this.form.email = this.currentUser.email;
    this.userService.registerPatientInfo(this.form).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.infoRegisteredFailed = false;
        window.location.reload();
      },
      err => {
        this.errorMessage = err.error.message;
        this.infoRegisteredFailed = true;
      }
    );
  }

  ngOnDestroy() {
    this.unsubscribe$.next;
   }
}
