import { Moment } from 'moment';

export interface IVisit {
  id?: number;
  visitDate?: Moment;
  description?: string;
  petName?: string;
  petId?: number;
}

export class Visit implements IVisit {
  constructor(public id?: number, public visitDate?: Moment, public description?: string, public petName?: string, public petId?: number) {}
}
