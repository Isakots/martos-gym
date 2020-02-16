import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./layout/home/home.component";
import {AboutUsComponent} from "./layout/about-us/about-us.component";
import {NutritionComponent} from "./layout/nutrition/nutrition.component";
import {GymInfoComponent} from "./layout/gym-info/gym-info.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'about-us',
    component: AboutUsComponent
  },
  {
    path: 'gym-info',
    component: GymInfoComponent
  },
  {
    path: 'nutrition',
    component: NutritionComponent
  },
  {
    path: 'login',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
