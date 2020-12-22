import { NgModule } from '@angular/core';
import { HasRoleDirective } from './directives/has-role.directive';

@NgModule({
    declarations: [
        HasRoleDirective
    ],
    exports: [
        HasRoleDirective
    ]
})
export class SharedModule {
}
