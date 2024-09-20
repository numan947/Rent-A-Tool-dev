import {Component, OnInit} from '@angular/core';
import {AuthenticationRequestDto} from "../../services/models/authentication-request-dto";
import {Router} from "@angular/router";
import {AuthenticationResponseDto} from "../../services/models/authentication-response-dto";
import {KeycloakService} from "../../services/keycloak/keycloak.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  constructor(
    private router:Router,
    private keycloakService: KeycloakService,
  ) {
  }

  async ngOnInit(): Promise<void> {
        await this.keycloakService.init();
        await this.keycloakService.login();
    }
  //
  // authRequest:AuthenticationRequestDto = {
  //   email: "",
  //   password: ""
  // };
  // errorMessage:Array<string> = [];
  //
  // login() {
  //   this.errorMessage = [];
  //   this.authService.authenticate({
  //     body: this.authRequest
  //   }).subscribe(
  //     {
  //       next: (res:AuthenticationResponseDto) => {
  //         this.tokenService.token = res.token as string;
  //         this.router.navigate(['tools']);
  //       },
  //       error: (err) => {
  //         if (err.error.validationErrors){
  //           this.errorMessage = err.error.validationErrors;
  //         }else{
  //           this.errorMessage.push(err.error.businessErrorDescription)
  //         }
  //       }
  //     }
  //   )
  // }
  //
  // register() {
  //   this.router.navigate(['register']);
  // }
}
