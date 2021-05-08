import { Moment } from 'moment';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IMatch {
  id?: number;
  dateMatch?: Moment;
  competitionId?: number;
  utilisateurs?: IUtilisateur[];
}

export class Match implements IMatch {
  constructor(public id?: number, public dateMatch?: Moment, public competitionId?: number, public utilisateurs?: IUtilisateur[]) {}
}
