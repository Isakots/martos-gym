export interface ManagedUser {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  hasTicketForActivePeriod: boolean;
  authorities: string[];
}
