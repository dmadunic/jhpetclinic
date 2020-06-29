import { Moment } from 'moment';
import { IVisit } from './visit.model';
export interface IPet {
  id?: number;
  name?: string;
  birthDate?: Moment;
  typeName?: string;
  typeId?: number;
  ownerLastName?: string;
  ownerId?: number;
  visits?: IVisit[];
}

export class Pet implements IPet {
  constructor(
    public id?: number,
    public name?: string,
    public birthDate?: Moment,
    public typeName?: string,
    public typeId?: number,
    public ownerLastName?: string,
    public ownerId?: number,
    public visits?: IVisit[]
  ) {}
}
