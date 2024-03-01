import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { of } from 'rxjs';
import { MockService } from '../utils/mock-service.spec';

import { Router } from '@angular/router';
import { User } from '../core/models/user.model';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService;
  let tokenStorageService;
  let router;

  const user: User = {
    id: 1,
    firstName: 'firstname',
    postCode: '75001',
    lastName: 'last name',
    address: 'Rue du faubourg St-Honoré',
    city: 'Paris',
    longitude: {
      min: 0,
      max: 100
    },
    latitude: {
      min: 0,
      max: 100
    },
    phone: '000000000',
    birthdate: '12/05/2000',
    email: 'email@test.com',
    socialSecurityNumber: '351611',
    userId: 1,
    roles:  ['user', 'admin']
  };

  const jwtResponse = {
    accessToken: 'accessToken',
    id: 1,
    email: 'test@test.com',
    roles: ['user', 'admin']
  };

  beforeEach(() => {
    authService = MockService.mock('AuthService', ['login', 'register']);
    tokenStorageService = MockService.mock('TokenStorageService', ['signOut', 'saveToken', 'getToken', 'saveUser', 'getUser']);
    router = MockService.mock('Router', ['navigate']);
  });



  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: TokenStorageService, useValue: tokenStorageService },
        { provide: Router, useValue: router }
      ],
      imports: [RouterTestingModule]
    }).overrideComponent(LoginComponent, {
      // Setter le template à la valeur '' pour nepas gérer le code html.
      // On se concentre uniquement sur la logique métier.
      set: {
        template: ''
      }
    }).compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    tokenStorageService.getToken.and.returnValue('token');
    tokenStorageService.getUser.and.returnValue(user);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect( component.isLoggedIn).toEqual(true);
    expect(tokenStorageService.getToken).toHaveBeenCalled();
    expect(tokenStorageService.getUser).toHaveBeenCalled();
  });

});