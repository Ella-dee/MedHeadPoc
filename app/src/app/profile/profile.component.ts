import { Component, OnInit, Input } from '@angular/core';

import { TokenStorageService } from '../_services/token-storage.service';
import { User } from '../core/models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  @Input() user!: User;  

  constructor(private token: TokenStorageService) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    //this.user = this.userService.getUserContent(1);
  }
}
