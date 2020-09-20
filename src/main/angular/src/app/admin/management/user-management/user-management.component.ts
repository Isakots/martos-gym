import {Component, Input, OnInit} from '@angular/core';
import {ManagedUser} from '../../../shared/domain/managed-user';
import {FormArray, FormBuilder} from '@angular/forms';
import {ManagedUserService} from '../../../shared/service/managed-user.service';
import {UserNotificationService} from '../../../shared/service/user-notification.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  @Input()
  managedUsers: ManagedUser[];

  modifiedUsers: ManagedUser[];

  managedUserFormArray: FormArray;

  constructor(
    private formBuilder: FormBuilder,
    private managedUserService: ManagedUserService,
    private userNotificationService: UserNotificationService) {
    this.managedUserFormArray = this.formBuilder.array([]);
  }

  ngOnInit(): void {
    this.modifiedUsers = [];

    this.managedUserFormArray = this.formBuilder.array(
      this.managedUsers.sort((user1, user2) => {
        return user1.lastName <= user2.lastName ? 1 : -1;
      })
    );

    this.managedUsers.map(managedUser => {
      return this.formBuilder.control(managedUser);
    });
  }

  addUserToModifiedList(formControlModel: ManagedUser) {
    if (!this.modifiedUsers.includes(formControlModel, 0)) {
      this.modifiedUsers.push(formControlModel);
    }
  }

  saveManagedUsers() {
    this.managedUserService.updateAll(this.modifiedUsers).subscribe(
      response => {
        this.managedUsers = response.body;
        this.userNotificationService.notifyUser('A bérletek és a felhasználói szerepkörek mentése sikeres.', false);
        window.scroll(0, 0);
      },
      () => {
        this.userNotificationService.notifyUser('Hiba történt a bérletek és felhasználói szerepkörek mentése közben.', true);
        window.scroll(0, 0);
      }
    );
  }
}
