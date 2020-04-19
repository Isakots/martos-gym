import {Component, Input, OnInit} from "@angular/core";
import {TrainingModel} from "../../shared/domain/training-model";

@Component({
  selector: 'app-training-item',
  templateUrl: './training-item.component.html',
  styleUrls: ['./training-item.component.scss']
})
export class TrainingItemComponent implements OnInit {

  @Input()
  training: TrainingModel;

  constructor() {
  }

  ngOnInit(): void {

  }


}
