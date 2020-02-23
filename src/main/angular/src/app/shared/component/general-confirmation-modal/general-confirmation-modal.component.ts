import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-general-confirmation-modal',
  templateUrl: './general-confirmation-modal.component.html',
  styleUrls: ['./general-confirmation-modal.component.scss']
})
export class GeneralConfirmationModalComponent implements OnInit {
  @ViewChild('confirmationView') private confirmationView;
  protected modalReference: NgbModalRef;
  closeResult: string;

  @Output()
  public eventConfirmed: EventEmitter<boolean> = new EventEmitter();

  constructor(protected modalService: NgbModal) {
  }

  ngOnInit() {
  }

  public open() {
    this.modalReference = this.modalService.open(this.confirmationView, {centered: true});
    this.modalReference.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this._getDismissReason(reason)}`;
    });
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

  confirmEvent(value: boolean) {
    this.eventConfirmed.emit(value);
    this.modalReference.close();
  }

}
