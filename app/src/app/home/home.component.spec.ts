import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { MockService } from '../utils/mock-service.spec';
import { User } from '../core/models/user.model';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let userService;
  let tokenStorageService;


  beforeEach(() => {
    userService = MockService.mock('UserService', ['getPublicContent', 'getUserContent',
      'getUserByEmail', 'getNearestHospital', 'registerPatientInfo', 'getAllSpecialityGroups', 'getSpecialitiesBySpecialityGroupByName',
    'getSpecialitiesBySpecialityGroupById', 'getPerimeter']);
    tokenStorageService = MockService.mock('TokenStorageService', ['signOut', 'saveToken', 'getToken', 'saveUser', 'getUser']);
  });

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
    roles:  ['']
  };



  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      providers: [
        { provide: UserService, useValue: userService },
        { provide: TokenStorageService, useValue: tokenStorageService }
      ],
    }).overrideComponent(HomeComponent, {
      // Setter le template à la valeur '' pour ne pas avoir à gérer le code html.
      // On se concentre uniquement sur la logique métier contenu dans le code Typescript.
      set: {
        template: ''
      }
    }).compileComponents();
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    tokenStorageService.getToken.and.returnValue('token');
    tokenStorageService.getUser.and.returnValue(user);
    userService.getUserByEmail.and.returnValue(of(user));
    userService.getUserContent.and.returnValue(of(user));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(tokenStorageService.getToken).toHaveBeenCalled();
    expect(tokenStorageService.getUser).toHaveBeenCalled();
    expect(userService.getUserByEmail).toHaveBeenCalled();
    expect(userService.getUserContent).toHaveBeenCalled();
    expect( component.isLoggedIn).toEqual(true);
  });
});