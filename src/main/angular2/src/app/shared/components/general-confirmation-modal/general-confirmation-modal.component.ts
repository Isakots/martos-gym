import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { ModalDismissReasons, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationType } from '../../constants';

@Component({
    selector: 'app-general-confirmation-modal',
    templateUrl: './general-confirmation-modal.component.html'
})
export class GeneralConfirmationModalComponent implements OnInit {
    @Input() confirmationType: ConfirmationType = ConfirmationType.DELETION;
    @Output() readonly eventConfirmed: EventEmitter<boolean> = new EventEmitter();
    @ViewChild('confirmationView') private readonly confirmationView!: TemplateRef<any>;

    readonly CONFIRMATION_TYPE_ENUM = ConfirmationType;
    closeResult!: string;
    confirmationText!: string;

    private modalReference!: NgbModalRef;

    constructor(protected modalService: NgbModal,
                private readonly translate: TranslateService) {
    }

    ngOnInit(): void {
        // eslint-disable-next-line default-case
        switch (this.confirmationType) {
            case ConfirmationType.DELETION:
                this.confirmationText = this.translate.instant('CONFIRMATION_MODAL.CONFIRM_DELETION_TEXT');
                break;
            case ConfirmationType.EMAIL:
                this.confirmationText = this.translate.instant('CONFIRMATION_MODAL.CONFIRM_EMAIL_TEXT');
                break;
        }
    }

    open(): void {
        this.modalReference = this.modalService.open(this.confirmationView, {centered: true});
        this.modalReference.result.then(result => {
            this.closeResult = `Closed with: ${result}`;
        }, reason => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    confirmEvent(value: boolean): void {
        this.eventConfirmed.emit(value);
        this.modalReference.close();
    }

    // eslint-disable-next-line class-methods-use-this
    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

}
