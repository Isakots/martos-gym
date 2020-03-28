import {NgModule} from "@angular/core";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ToolUpdateComponent} from "./tool-update/tool-update.component";
import {ToolRoutingModule} from "./tool-routing.module";
import {ToolViewComponent} from "./tool-view/tool-view.component";
import {ToolItemComponent} from "./tool-item/tool-item.component";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [
    ToolRoutingModule,
    FontAwesomeModule,
    CommonModule
  ],
  declarations: [ToolViewComponent, ToolUpdateComponent, ToolItemComponent]
})
export class ToolModule {
}
