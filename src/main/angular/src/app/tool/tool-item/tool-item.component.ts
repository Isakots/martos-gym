import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from "@angular/core";
import {Tool} from "../../shared/domain/tool";
import {GeneralConfirmationModalComponent} from "../../shared/component/general-confirmation-modal/general-confirmation-modal.component";
import {ToolService} from "../../shared/service/tool.service";
import {ToolUpdateModalComponent} from "../tool-update/tool-update-modal.component";

@Component({
  selector: 'app-tool-item',
  templateUrl: './tool-item.component.html',
  styleUrls: ['./tool-item.component.scss']
})
export class ToolItemComponent implements OnInit {
  @ViewChild(GeneralConfirmationModalComponent) confirmDeletionModal: GeneralConfirmationModalComponent;
  @ViewChild(ToolUpdateModalComponent) toolUpdateModal: ToolUpdateModalComponent;

  @Input()
  data: Tool;

  @Output()
  public toolModified: EventEmitter<boolean> = new EventEmitter();
  @Output()
  public toolDeleted: EventEmitter<boolean> = new EventEmitter();

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
  }

  openConfirmationModal() {
    this.confirmDeletionModal.open();
  }

  onEventConfirmation(confirmed: boolean) {
    if (confirmed) {
      this.toolService.delete(this.data.id).subscribe(
        () => {
          this.toolDeleted.emit(true);
        },
        () => {
          this.toolDeleted.emit(false);
        });
    }
  }

  openModificationModal() {
    this.toolUpdateModal.open();
  }

  onToolModifiedEvent(success: boolean) {
    this.toolModified.emit(success);
  }
}
