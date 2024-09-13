import { Component } from '@angular/core';
import {AuthenticationRequestDto} from "../../services/models/authentication-request-dto";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {AuthenticationResponseDto} from "../../services/models/authentication-response-dto";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(
    private router:Router,
    private authService:AuthenticationService,
    private tokenService:TokenService
  ) {
  }

  authRequest:AuthenticationRequestDto = {
    email: "",
    password: ""
  };
  errorMessage:Array<string> = [];

  login() {
    this.errorMessage = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe(
      {
        next: (res:AuthenticationResponseDto) => {
          this.tokenService.token = res.token as string;
          this.router.navigate(['tools']);
        },
        error: (err) => {
          if (err.error.validationErrors){
            this.errorMessage = err.error.validationErrors;
          }else{
            this.errorMessage.push(err.error.businessErrorDescription)
          }
        }
      }
    )
  }

  register() {
    this.router.navigate(['register']);
  }
}
