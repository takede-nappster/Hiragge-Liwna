import { Moment } from 'moment';

export interface IJeux {
  id?: number;
  nom?: string;
  dateCreation?: Moment;
  concepteur?: string;
  prix?: number;
  meilleurScore?: number;
  lienJouer?: string;
  logoContentType?: string;
  logo?: any;
  setupFileContentType?: string;
  setupFile?: any;
  description?: string;
  competitionId?: number;
}

export class Jeux implements IJeux {
  constructor(
    public id?: number,
    public nom?: string,
    public dateCreation?: Moment,
    public concepteur?: string,
    public prix?: number,
    public meilleurScore?: number,
    public lienJouer?: string,
    public logoContentType?: string,
    public logo?: any,
    public setupFileContentType?: string,
    public setupFile?: any,
    public description?: string,
    public competitionId?: number
  ) {}
}
