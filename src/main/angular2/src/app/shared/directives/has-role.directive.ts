import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AccountService } from '../../core/services/account.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {
  @Input() set appHasRole(role: string) {
    this.accountService.getAuthenticationStateObs().subscribe(() => {
      if (this.accountService.hasRole(role) || (!this.accountService.isAuthenticated() && 'ROLE_ANONYMOUS' === role)) {
        this.viewContainer.createEmbeddedView(this.template);
      } else {
        this.viewContainer.clear();
      }
    });
  }

  constructor(private readonly template: TemplateRef<any>,
              private readonly viewContainer: ViewContainerRef,
              private readonly accountService: AccountService) {
  }
}
