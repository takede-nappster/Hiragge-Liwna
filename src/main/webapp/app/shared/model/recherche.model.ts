export interface IRecherche {
  id?: number;
}

export class Recherche implements IRecherche {
  constructor(public id?: number) {}
}
