import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationType} from '../../constants';

@Component({
  selector: 'app-general-confirmation-modal',
  templateUrl: './general-confirmation-modal.component.html'
})
export class GeneralConfirmationModalComponent implements OnInit {
  @ViewChild('confirmationView') private confirmationView;
  protected modalReference: NgbModalRef;
  closeResult: string;

  CONFIRMATION_TYPE_ENUM = ConfirmationType;

  @Input()
  confirmationType: ConfirmationType = ConfirmationType.DELETION;

  @Output()
  public eventConfirmed: EventEmitter<boolean> = new EventEmitter();

  confirmationText: string;

  constructor(protected modalService: NgbModal) {
  }

  ngOnInit() {
    switch (this.confirmationType) {
      case ConfirmationType.DELETION:
        this.confirmationText = 'Biztos, hogy törölni szeretné?';
        break;
      case ConfirmationType.EMAIL:
        this.confirmationText = 'Biztos, hogy elküldi az e-maileket?';
        break;
      case ConfirmationType.PERIOD_CLOSE:
        this.confirmationText = 'Biztos, hogy lezárod az időszakot? Az időszak újranyitására nincs lehetőség!';
        break;
    }
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
