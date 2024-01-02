import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [HttpClientModule, FormsModule],
    });
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form should be invalid', () => {
    expect(component.form.valid).toBeFalsy();
});

it('should submit the form when the submit button is clicked', () => {
  const compiled = fixture.debugElement.nativeElement;
  const btnEl = compiled.querySelector('#submit-btn');
  const fnc = spyOn(component, 'onSubmit');

  btnEl.click();
  fixture.detectChanges();

  expect(fnc).toHaveBeenCalled();
})

it('should display alert message when onSubmit is called but form is invalid', () => {
  const compiled = fixture.debugElement.nativeElement;  
  const btnEl = compiled.querySelector('#submit-btn');  
  expect(compiled.querySelector('#errorEmailEmpty')).toEqual(null);
  btnEl.click();
  component.onSubmit();
  fixture.detectChanges();
  console.log("form email: ",component.form.email);
  const afterClickCompiled = fixture.debugElement.nativeElement;   
  expect(afterClickCompiled.querySelector('#errorEmailEmpty')).not.toEqual(null);  
 // expect(afterClickCompiled.querySelector('#errorEmailEmpty').innerText).toEqual('Email is required');
  //expect(afterClickCompiled.querySelector('#errorPasswordEmpty').innerText).toEqual('Password is required');
 
})

});
