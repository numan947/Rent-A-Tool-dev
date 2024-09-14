import { Component } from '@angular/core';
import {RegistrationRequestDto} from "../../services/models/registration-request-dto";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {
  }

  registrationRequest: RegistrationRequestDto = {
    email: '',
    firstname: '',
    lastname: '',
    password: ''
  };

  errorMessages: Array<string> = [];

  register() {
    this.errorMessages = [];
    this.authService.register({
      body: this.registrationRequest
    }).subscribe({
      next: () => {
        this.router.navigate(['activate-account']);
      },
      error: (error) => {
        if (error.status === 400) {
          this.errorMessages = error.error.validationErrors;
        } else {
          this.errorMessages.push(error.error.error);
        }
      }
    });


  }

  login() {
    this.router.navigate(['/login']);
  }
}
