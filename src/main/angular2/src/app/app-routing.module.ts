import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NutritionComponent } from './pages/nutrition/nutrition.component';
import { RulesComponent } from './pages/rules/rules.component';
import { SignUpComponent } from './pages/sign-up/sign-up.component';

const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: HomeComponent
    },
    {
        path: 'nutrition',
        component: NutritionComponent
    },
    {
        path: 'rules',
        component: RulesComponent
    },
    {
        path: 'sign-up',
        component: SignUpComponent
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
export class AppRoutingModule {
}
