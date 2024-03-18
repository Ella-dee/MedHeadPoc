import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BookingComponent } from './booking.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

describe('BookingComponent', () => {
  let component: BookingComponent;
  let fixture: ComponentFixture<BookingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookingComponent],
      imports: [HttpClientModule, FormsModule],
      providers: [
        { provide: MatDialog, useValue: {} },
      ]
    });
    fixture = TestBed.createComponent(BookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});



/*
import { ComponentFixture, TestBed, getTestBed } from '@angular/core/testing';
import { BookingComponent } from './booking.component';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { MockService } from '../utils/mock-service.spec';
import { Observable, of } from 'rxjs';
import { SpecialtyGroup } from '../core/models/specialty-group.model';
import any = jasmine.any;
import { Specialty } from '../core/models/specialty';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';

fdescribe('BookingComponent', () => {
  let component: BookingComponent;
  let fixture: ComponentFixture<BookingComponent>;
  let userService: UserService;
  let tokenStorageService: TokenStorageService;
  let injector: TestBed;
  let sg1 = {} as SpecialtyGroup;
  let sp1 = {} as Specialty;


  beforeEach(() => {
    userService = MockService.mock('UserService', ['getPublicContent', 'getUserContent',
      'getUserByEmail', 'getNearestHospital', 'registerPatientInfo', 'getAllSpecialityGroups', 'getSpecialitiesBySpecialityGroupByName',
      'getSpecialitiesBySpecialityGroupById', 'getPerimeter']);
    tokenStorageService = MockService.mock('TokenStorageService', ['signOut', 'saveToken', 'getToken', 'saveUser', 'getUser']);
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookingComponent],
      providers: [
        { provide: UserService, useValue: userService },
        { provide: TokenStorageService, useValue: tokenStorageService },  
        { provide: MatDialog, useValue: {} },
        { provide: HttpClient, useValue: {} },
      ],
    }).overrideComponent(BookingComponent, {
      // Setter le template à la valeur '' pour nepas gérer le code html.
      // On se concentre uniquement sur la logique métier.
      set: {
        template: ''
      }
    }).compileComponents();
    fixture = TestBed.createComponent(BookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    sp1.name= 'specialite';
    sp1.id=1;
    sg1.id = 1;
    sg1.name = 'something';
  });

  it('should create', () => {
    spyOn(userService, 'getAllSpecialityGroups').and.returnValue(of([sg1]));
    spyOn(userService, 'getSpecialitiesBySpecialityGroupByName').and.returnValue(of([sp1]));
    // userService.getAllSpecialityGroups.and.returnValue(of([specialtyGroup]));
    // userService.getSpecialitiesBySpecialityGroupByName = of([speciaty]);
    expect(component).toBeTruthy();
  });
});*/