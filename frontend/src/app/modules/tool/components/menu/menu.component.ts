import {Component, OnInit} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {KeycloakService} from "../../../../services/keycloak/keycloak.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  constructor(
    private keycloakService: KeycloakService,
  ) {
  }
  userName: string = '';
  ngOnInit(): void {
    this.getUserName();
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(
      link=>{
        if (window.location.href.endsWith(link.getAttribute('href')||'')) {
          link.classList.add('active');
        }
        link.addEventListener(
          'click',
          ()=>{
            linkColor.forEach(
              otherLink=>{
                otherLink.classList.remove('active');
              }
            )
            link.classList.add('active');
          }
        )
      }
    )
  }

  logout() {
    this.keycloakService.logout().then(r => {
      window.location.reload();
    });
  }

  private getUserName() {
    // TODO: get user name from token
  }
}
