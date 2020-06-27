import { ISpecialty } from 'app/shared/model/specialty.model';

export interface IVet {
  id?: number;
  firstName?: string;
  lastName?: string;
  specialties?: ISpecialty[];
}

export class Vet implements IVet {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public specialties?: ISpecialty[]) {}
}
