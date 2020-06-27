import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPetType } from 'app/shared/model/pet-type.model';

type EntityResponseType = HttpResponse<IPetType>;
type EntityArrayResponseType = HttpResponse<IPetType[]>;

@Injectable({ providedIn: 'root' })
export class PetTypeService {
  public resourceUrl = SERVER_API_URL + 'api/pet-types';

  constructor(protected http: HttpClient) {}

  create(petType: IPetType): Observable<EntityResponseType> {
    return this.http.post<IPetType>(this.resourceUrl, petType, { observe: 'response' });
  }

  update(petType: IPetType): Observable<EntityResponseType> {
    return this.http.put<IPetType>(this.resourceUrl, petType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPetType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPetType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
