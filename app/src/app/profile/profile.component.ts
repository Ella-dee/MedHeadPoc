import { Component, OnInit, Input } from '@angular/core';

import { TokenStorageService } from '../_services/token-storage.service';
import { User } from '../core/models/user.model';
import { UserService } from '../_services/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  @Input() user!: User;  
  patient!: any; 

  constructor(private token: TokenStorageService, private userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    this.userService.getUserContent(this.currentUser.id).subscribe(
      (data) => {
        this.patient = data;
        console.log("Test: patient: ", this.patient);
        console.log("Adresse: ", this.patient.address);
      }
    );
  }
}
