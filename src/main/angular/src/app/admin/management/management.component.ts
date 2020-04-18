import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ManagedUser} from "../../shared/domain/managed-user";
import {GymPeriod} from "../../shared/domain/gym-period";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html'
})
export class ManagementComponent implements OnInit {

  periods: GymPeriod[];
  managedUsers: ManagedUser[];

  constructor(protected activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.subscribe(({gymPeriods, managedUsers}) => {
        this.periods = gymPeriods;
        this.managedUsers = managedUsers;
      }
    );
  }

  ngOnInit(): void {

  }

}
