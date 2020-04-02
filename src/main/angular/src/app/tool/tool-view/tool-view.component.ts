import {Component, OnInit, ViewChild} from "@angular/core";
import {ToolService} from "../../shared/service/tool.service";
import {Tool} from "../../shared/domain/tool";
import {ToolUpdateModalComponent} from "../tool-update/tool-update-modal.component";

@Component({
  selector: 'app-tool-view',
  templateUrl: './tool-view.component.html'
})
export class ToolViewComponent implements OnInit {
  @ViewChild(ToolUpdateModalComponent) toolCreationModal: ToolUpdateModalComponent;

  tools: Tool[] = [];

  creationSuccess: boolean;
  creationFailure: boolean;
  deletionSuccess: boolean;
  deletionFailure: boolean;

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
    this.creationSuccess = false;
    this.creationFailure = false;
    this.deletionSuccess = false;
    this.deletionFailure = false;
    this._findAllTools();
  }

  private _findAllTools() {
    this.toolService.findAll().subscribe(data => {
      this.tools = data.body;
    });
  }

  public get reservableTools() {
    return this.tools.filter(tool => tool.reachable);
  }

  public get notReservableTools() {
    return this.tools.filter(tool => !tool.reachable);
  }

  open() {
    this.toolCreationModal.open();
  }

  onToolCreatedEvent(success: boolean) {
    if (success) {
      this.creationSuccess = true;
      setTimeout(() => {
        this.creationSuccess = false;
      }, 3000);
      this._findAllTools();
    } else {
      this.creationFailure = true;
      setTimeout(() => {
        this.creationFailure = false;
      }, 3000);
      this._findAllTools();
    }
  }

  onToolDeletedEvent(success: boolean) {
    if (success) {
      this.deletionSuccess = true;
      setTimeout(() => {
        this.deletionSuccess = false;
      }, 3000);
      this._findAllTools();
    } else {
      this.deletionFailure = true;
      setTimeout(() => {
        this.deletionFailure = false;
      }, 3000);
      this._findAllTools();
    }
  }
}
