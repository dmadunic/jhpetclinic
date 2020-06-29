import { Moment } from 'moment';
import { IVisit } from 'app/shared/model/visit.model';

export interface IPet {
  id?: number;
  name?: string;
  birthDate?: Moment;
  visits?: IVisit[];
  typeName?: string;
  typeId?: number;
  ownerLastName?: string;
  ownerId?: number;
}

export class Pet implements IPet {
  constructor(
    public id?: number,
    public name?: string,
    public birthDate?: Moment,
    public visits?: IVisit[],
    public typeName?: string,
    public typeId?: number,
    public ownerLastName?: string,
    public ownerId?: number
  ) {}
}
