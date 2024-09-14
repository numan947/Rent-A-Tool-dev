import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
  message: string = '';
  isActivated: boolean = false;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}


  onCodeCompleted(activationToken: string) {
    this.submitted = true;
    this.authService.activate({
      token: activationToken
    }).subscribe(
      {
        next: () => {
          this.isActivated = true;
          this.message = 'Account activated successfully';
        },
        error: (err) => {
          this.message = err.error.businessErrorDescription;
          this.isActivated = false;
        }
      }
    )
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }
}
