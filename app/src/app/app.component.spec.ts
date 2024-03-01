import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AuthService } from './_services/auth.service';
import { TokenStorageService } from './_services/token-storage.service';
import { MockService } from './utils/mock-service.spec';
import { UserService } from './_services/user.service';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { Router } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';

describe('AppComponent', () => {
  let authService: AuthService;
  let userService: UserService;
  let tokenStorageService: TokenStorageService;
  let router: Router;

  // constructor(private tokenStorageService: TokenStorageService, private userService: UserService,library: FaIconLibrary, private router: Router) {
  //   library.addIcons(faUser);
  // }
  //
  beforeEach(() => {
    userService = MockService.mock('UserService', ['getPublicContent', 'getUserContent',
      'getUserByEmail', 'getNearestHospital', 'registerPatientInfo', 'getAllSpecialityGroups', 'getSpecialitiesBySpecialityGroupByName',
      'getSpecialitiesBySpecialityGroupById', 'getPerimeter']);
    authService = MockService.mock('AuthService', ['login', 'register']);
    tokenStorageService = MockService.mock('TokenStorageService', ['signOut', 'saveToken', 'getToken', 'saveUser', 'getUser']);
    router = MockService.mock('Router', ['navigate']);
  });


  beforeEach(() => TestBed.configureTestingModule({
    imports: [RouterTestingModule],
    declarations: [AppComponent],
    providers: [
      { provide: UserService, useValue: userService },
      { provide: AuthService, useValue: authService },
      { provide: TokenStorageService, useValue: tokenStorageService },
      { provide: Router, useValue: router }
    ],
  }).overrideComponent(AppComponent, {
    // Setter le template à la valeur '' pour nepas gérer le code html.
    // On se concentre uniquement sur la logique métier.
    set: {
      template: ''
    }
  }).compileComponents());

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'app'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('app');
  });

});