import {Component, OnInit} from "@angular/core";
import {LoginService} from "../../shared/service/login.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComonent implements OnInit {
  isNavbarCollapsed: boolean;

  constructor(private loginService:LoginService) {
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  logout() {
    this.loginService.logout();
  }

  showUserNavItem() {
    return this.loginService.isUserLoggedIn();
  }

  showAdminNavItem() {

  }

  login() {
    this.collapseNavbar();
  }
}
