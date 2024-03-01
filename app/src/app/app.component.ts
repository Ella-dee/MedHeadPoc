import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';
import { UserService } from './_services/user.service';
import { Subject, takeUntil } from 'rxjs';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private roles!: string[];
  isLoggedIn = false;
  registeredUser!: any;
  patient!: any;
  private readonly unsubscribe$ = new Subject();
  title: string = 'app';

  constructor(private tokenStorageService: TokenStorageService, private userService: UserService,library: FaIconLibrary, private router: Router) { 
    library.addIcons(faUser);
  }
 
  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.registeredUser =this.userService.getUserByEmail(user.email).pipe(
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
  }

  logout() {
    this.tokenStorageService.signOut();    
    this.router.navigate(['/home']);
  }
}
