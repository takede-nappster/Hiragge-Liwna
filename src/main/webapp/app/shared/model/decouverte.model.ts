export interface IDecouverte {
  id?: number;
}

export class Decouverte implements IDecouverte {
  constructor(public id?: number) {}
}
