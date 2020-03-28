import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {Tool} from "../../shared/domain/tool";

@Component({
  selector: 'app-tool-update',
  templateUrl: './tool-update.component.html'
})
export class ToolUpdateComponent implements OnInit {

  toolUpdateForm: FormGroup;

  constructor(protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._initFormGroup();
    this.activatedRoute.data.subscribe(({tool}) => {
      this.updateForm(tool);
    });
  }

  private _initFormGroup() {
    this.toolUpdateForm = new FormGroup({
      id: new FormControl(''),
      name: new FormControl(''),
      quantity: new FormControl('')
    });
  }

  updateForm(tool: Tool) {
    this.toolUpdateForm.patchValue({
      id: tool.id,
      name: tool.name,
      quantity: tool.quantity
    });
  }

}
