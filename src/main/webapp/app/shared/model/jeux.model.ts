import { Moment } from 'moment';

export interface IJeux {
  id?: number;
  nom?: string;
  description?: string;
  dateCreation?: Moment;
  concepteur?: string;
  prix?: number;
  meilleurScore?: number;
  lienTelechargement?: string;
  lienJouer?: string;
  competitionId?: number;
}

export class Jeux implements IJeux {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public dateCreation?: Moment,
    public concepteur?: string,
    public prix?: number,
    public meilleurScore?: number,
    public lienTelechargement?: string,
    public lienJouer?: string,
    public competitionId?: number
  ) {}
}
