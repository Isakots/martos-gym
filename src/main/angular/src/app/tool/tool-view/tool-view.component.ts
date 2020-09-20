import {Component, OnInit, ViewChild} from '@angular/core';
import {ToolService} from '../../shared/service/tool.service';
import {Tool} from '../../shared/domain/tool';
import {ToolUpdateModalComponent} from '../tool-update/tool-update-modal.component';
import {UserNotificationService} from '../../shared/service/user-notification.service';

@Component({
  selector: 'app-tool-view',
  templateUrl: './tool-view.component.html'
})
export class ToolViewComponent implements OnInit {
  @ViewChild(ToolUpdateModalComponent) toolCreationModal: ToolUpdateModalComponent;

  tools: Tool[] = [];

  constructor(
    private toolService: ToolService,
    private userNotificationService: UserNotificationService
  ) {
  }

  ngOnInit(): void {
    this._findAllTools();
  }

  private _findAllTools() {
    this.toolService.findAll().subscribe(data => {
      this.tools = data.body;
    });
  }

  public get reservableTools() {
    return this.tools.filter(tool => tool.reachable);
  }

  public get notReservableTools() {
    return this.tools.filter(tool => !tool.reachable);
  }

  open() {
    this.toolCreationModal.open();
  }

  onToolCreatedEvent(success: boolean) {
    if (success) {
      this.userNotificationService.notifyUser('Eszköz rögzítése sikeres!', false);
    } else {
      this.userNotificationService.notifyUser('Eszköz rögzítése sikertelen!', true);
    }
    this._findAllTools();
  }

  onToolDeletedEvent(success: boolean) {
    if (success) {
      this.userNotificationService.notifyUser('Eszköz törlése sikeres!', false);
    } else {
      this.userNotificationService.notifyUser('Eszköz törlése sikertelen!', true);
    }
    this._findAllTools();
  }
}
