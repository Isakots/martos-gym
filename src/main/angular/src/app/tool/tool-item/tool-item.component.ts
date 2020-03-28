import {Component, Input, OnInit} from "@angular/core";
import {Tool} from "../../shared/domain/tool";

@Component({
  selector: 'app-tool-item',
  templateUrl: './tool-item.component.html'
})
export class ToolItemComponent implements OnInit {

  @Input()
  data: Tool;

  constructor() {
  }

  ngOnInit(): void {
  }

}
