import { IVet } from 'app/shared/model/vet.model';

export interface ISpecialty {
  id?: number;
  name?: string;
  vets?: IVet[];
}

export class Specialty implements ISpecialty {
  constructor(public id?: number, public name?: string, public vets?: IVet[]) {}
}
