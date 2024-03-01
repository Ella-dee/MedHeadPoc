import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { AuthService } from '../_services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { MockService } from '../utils/mock-service.spec';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService;
  let router: Router;

  beforeEach(() => {
    authService = MockService.mock('AuthService', ['login', 'register']);
    router = MockService.mock('Router', ['navigate']);
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router }
      ],
      imports: [RouterTestingModule]
    }).overrideComponent(RegisterComponent, {
      // Setter le template à la valeur '' pour nepas gérer le code html.
      // On se concentre uniquement sur la logique métier.
      set: {
        template: ''
      }
    }).compileComponents();
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('register new user', () => {
    // GIVEN
    authService.register.and.returnValue(of({}));
    component.onSubmit();

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(authService.register).toHaveBeenCalledTimes(1);
    expect(router.navigate).toHaveBeenCalledTimes(1);
  });
});