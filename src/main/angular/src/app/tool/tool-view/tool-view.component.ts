import {Component, OnInit} from "@angular/core";
import {ToolService} from "../../shared/service/tool.service";
import {Tool} from "../../shared/domain/tool";

@Component({
  selector: 'app-tool-view',
  templateUrl: './tool-view.component.html'
})
export class ToolViewComponent implements OnInit {

  tools: Tool[];

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
    this.toolService.findAll().subscribe(data => {
      this.tools = data.body;
    })
  }

}
