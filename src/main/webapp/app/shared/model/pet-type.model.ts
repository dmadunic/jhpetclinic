export interface IPetType {
  id?: number;
  name?: string;
}

export class PetType implements IPetType {
  constructor(public id?: number, public name?: string) {}
}
