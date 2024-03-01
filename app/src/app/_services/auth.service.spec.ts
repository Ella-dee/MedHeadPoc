import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('AuthService', () => {
  let service: AuthService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule],
      providers: [AuthService],
    }).compileComponents();
    service = TestBed.inject(AuthService);
    // Inject the http service and test controller for each test
    httpClient = TestBed.get(HttpClient);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call login and return the user from the API', () => {
    const credentials = {
      username: 'user',
      password: 'password'
    };
    const user = {
      accessToken: "accessToken",
      username: "test_user",
      email: "mail_test@gmail.com"
    };
    service.login(credentials).subscribe((data) => {
      expect(data).toEqual(user);
    });

    const req = httpTestingController.expectOne({
      method: 'POST'
    });

    req.flush(user);
  });

  it('should call register and return the user from the API', () => {
    const user = {
      username: 'user',
      password: 'password'
    };
    const messageResponse = {
      message: "User registered successfully!"
    }
    service.register(user).subscribe((data) => {
      expect(data).toEqual(messageResponse);
    });

    const req = httpTestingController.expectOne({
      method: 'POST'
    });

    req.flush(messageResponse);
  });
});
