import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {KeycloakService} from "../keycloak/keycloak.service";

@Injectable()
export class JwtTokenInterceptor implements HttpInterceptor {

  constructor(
    private keycloakService: KeycloakService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.keycloakService.keycloak?.token;
    if (token){
      const authenticatedReq = request.clone({
       headers: new  HttpHeaders(
         {
           Authorization: `Bearer ${token}`
         }
       )
      });
      return next.handle(authenticatedReq);
    }
    return next.handle(request);
  }
}
