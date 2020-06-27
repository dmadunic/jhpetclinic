import { IPet } from 'app/shared/model/pet.model';

export interface IOwner {
  id?: number;
  firstName?: string;
  lastName?: string;
  address?: string;
  city?: string;
  telephone?: string;
  pets?: IPet[];
}

export class Owner implements IOwner {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public address?: string,
    public city?: string,
    public telephone?: string,
    public pets?: IPet[]
  ) {}
}
