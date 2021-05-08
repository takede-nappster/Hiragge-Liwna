import { IMatch } from 'app/shared/model/match.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

export interface IUtilisateur {
  id?: number;
  userType?: UserType;
  matches?: IMatch[];
}

export class Utilisateur implements IUtilisateur {
  constructor(public id?: number, public userType?: UserType, public matches?: IMatch[]) {}
}
