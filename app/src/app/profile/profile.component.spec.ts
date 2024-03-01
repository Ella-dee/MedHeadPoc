import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { MockService } from '../utils/mock-service.spec';
import { User } from '../core/models/user.model';
import { of } from 'rxjs';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
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
      declarations: [ProfileComponent],
      providers: [
        { provide: UserService, useValue: userService },
        { provide: TokenStorageService, useValue: tokenStorageService }
      ],
    }).overrideComponent(ProfileComponent, {
      // Setter le template à la valeur '' pour nepas gérer le code html.
      // On se concentre uniquement sur la logique métier.
      set: {
        template: ''
      }
    }).compileComponents();
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    tokenStorageService.getToken.and.returnValue('token');
    tokenStorageService.getUser.and.returnValue(user);
    userService.getUserByEmail.and.returnValue(of(user));
    userService.getUserContent.and.returnValue(of(user));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });



});