import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../_services/user.service';
import { User } from 'src/app/core/models/user.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  @Input() user!: User;  
  users$!: Observable<User[]>;

  constructor(private userService: UserService) { }
  
  ngOnInit() {
    this.users$ = this.userService.getPublicContent();
    //this.userService.getPublicContent().subscribe((users) => { console.log(JSON.stringify(users));});
    //this.user = this.userService.getUserContent(1);
  }
}
