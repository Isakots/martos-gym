import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from "@angular/core";
import {Tool} from "../../shared/domain/tool";
import {ModalDismissReasons, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ToolService} from "../../shared/service/tool.service";

@Component({
  selector: 'app-tool-update-modal',
  templateUrl: './tool-update-modal.component.html'
})
export class ToolUpdateModalComponent implements OnInit {
  @ViewChild('toolFormView') private toolFormView;
  protected modalReference: NgbModalRef;
  closeResult: string;

  @Input()
  tool: Tool;

  @Output()
  public eventConfirmed: EventEmitter<boolean> = new EventEmitter();

  constructor(
    protected modalService: NgbModal,
    private toolService: ToolService) {
  }

  ngOnInit(): void {
    this._initTool();
  }

  public open() {
    this._initTool();
    this.modalReference = this.modalService.open(this.toolFormView, {centered: true});
    this.modalReference.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this._getDismissReason(reason)}`;
    });
  }

  close() {
    this.modalReference.close();
  }

  save() {
    if (this.tool.id == null) {
      this.toolService.create(this.tool).subscribe(
        () => {
          this.eventConfirmed.emit(true);
        },
        () => {
          this.eventConfirmed.emit(false);
        });
    } else {
      this.toolService.update(this.tool).subscribe(
        () => {
          this.eventConfirmed.emit(true);
        },
        () => {
          this.eventConfirmed.emit(false);
        });
    }
    this.tool = null;
    this.modalReference.close();
  }

  private _initTool() {
    if (this.tool === undefined || this.tool === null) {
      this.tool = {
        id: null,
        name: '',
        quantity: null,
        reachable: true
      }
    }
  }

  private _getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
