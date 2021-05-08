import { Moment } from 'moment';
import { IJeux } from 'app/shared/model/jeux.model';
import { IMatch } from 'app/shared/model/match.model';

export interface ICompetition {
  id?: number;
  nom?: string;
  description?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  nombreParticipant?: number;
  jeuxes?: IJeux[];
  matches?: IMatch[];
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public nombreParticipant?: number,
    public jeuxes?: IJeux[],
    public matches?: IMatch[]
  ) {}
}
