import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { HasRoleDirective } from './directives/has-role.directive';

@NgModule({
    declarations: [
        HasRoleDirective,
        MessageBoxComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        HasRoleDirective,
        MessageBoxComponent
    ]
})
export class SharedModule {
}
