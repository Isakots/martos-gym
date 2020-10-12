export interface ManagedUser {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  hasTicketForActivePeriod: boolean;
  authorities: string[];
}
