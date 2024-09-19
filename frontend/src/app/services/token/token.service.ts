import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  set token(token: string) {
    localStorage.setItem('token', token);
  }
  get token(): string {
    return localStorage.getItem('token') as string;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    if (!this.token) {
      return false;
    }
    // decode token
    const jwtHelper = new JwtHelperService();
    const isTokenExpired = jwtHelper.isTokenExpired(this.token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }
}
