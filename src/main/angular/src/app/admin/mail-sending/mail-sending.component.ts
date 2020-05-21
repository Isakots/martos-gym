import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {ChangeEvent} from "@ckeditor/ckeditor5-angular";
import {SendEmailService} from "../../shared/service/send-email.service";
import {UserNotificationService} from "../../shared/service/user-notification.service";
import {GeneralConfirmationModalComponent} from "../../shared/component/general-confirmation-modal/general-confirmation-modal.component";
import {ConfirmationType} from "../../shared/constants";

@Component({
  selector: 'app-mail-sending',
  templateUrl: './mail-sending.component.html'
})
export class MailSendingComponent implements OnInit {
  @ViewChild(GeneralConfirmationModalComponent) confirmEmailSendingModal: GeneralConfirmationModalComponent;

  readonly confirmationType = ConfirmationType.EMAIL;

  triedToSave: boolean = false;
  public ck5editor = ClassicEditor;
  mailForm: FormGroup;
  editorData: any;

  constructor(private _activatedRoute: ActivatedRoute,
              private _formBuilder: FormBuilder,
              private _sendEmailService: SendEmailService,
              private _userNotificationService: UserNotificationService) {
  }

  ngOnInit(): void {
    this._initFormGroup();
  }

  private _initFormGroup() {
    const formGroupControlsConfig = {
      topic: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      mailTo: ['Mindenki'],
    };
    this.editorData = '<p></p>';
    this.mailForm = this._formBuilder.group(formGroupControlsConfig);
  }

  showValidationMessage(formControl: AbstractControl) {
    return (formControl.invalid && (formControl.dirty || formControl.touched)) || (formControl.invalid && this.triedToSave);
  }

  public onChange({editor}: ChangeEvent) {
    this.editorData = editor.getData();
  }

  openConfirmationModal() {
    this.confirmEmailSendingModal.open();
  }

  validateFormOnSending() {
    this.triedToSave = true;
    this.mailForm.updateValueAndValidity();
    if (this.mailForm.invalid) {
      return;
    }
    this.openConfirmationModal();
  }

  sendEmail() {
    let emailRequestModel = {
      mailTo: this.mailForm.controls.mailTo.value === 'Mindenki' ? 'ALL' : 'MEMBERS_ONLY',
      topic: this.mailForm.controls.topic.value,
      content: this.editorData
    };

    this._sendEmailService.send(emailRequestModel).subscribe(() => {
        this._userNotificationService.notifyUser('Email-ek kiküldése folyamatban...', false);
      },
      () => {
        this._userNotificationService.notifyUser('Sikertelen emailküldés...', true);
      })

  }

  onEventConfirmation(confirmed: boolean) {
    if(confirmed) {
      this.sendEmail();
    }
  }
}
