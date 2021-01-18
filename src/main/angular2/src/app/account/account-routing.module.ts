import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorizationGuard } from '../core/guards/authorization.guard';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfileResolver } from './profile/profile.resolver';

const accountRoutes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'profile',
                component: ProfileComponent,
                resolve: {
                    account: ProfileResolver
                }
            },
            {
                path: 'change-password',
                component: ChangePasswordComponent
            }
        ],
        data: {
            authority: 'ROLE_USER'
        },
        canActivate: [AuthorizationGuard]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(accountRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AccountRoutingModule {
}
