import {NgModule} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ToolUpdateModalComponent} from './tool-update/tool-update-modal.component';
import {ToolRoutingModule} from './tool-routing.module';
import {ToolViewComponent} from './tool-view/tool-view.component';
import {ToolItemComponent} from './tool-item/tool-item.component';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    ToolRoutingModule,
    FontAwesomeModule,
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ToolViewComponent, ToolUpdateModalComponent, ToolItemComponent]
})
export class ToolModule {
}
