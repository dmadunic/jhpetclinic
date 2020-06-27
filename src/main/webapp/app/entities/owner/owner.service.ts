import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOwner } from 'app/shared/model/owner.model';

type EntityResponseType = HttpResponse<IOwner>;
type EntityArrayResponseType = HttpResponse<IOwner[]>;

@Injectable({ providedIn: 'root' })
export class OwnerService {
  public resourceUrl = SERVER_API_URL + 'api/owners';

  constructor(protected http: HttpClient) {}

  create(owner: IOwner): Observable<EntityResponseType> {
    return this.http.post<IOwner>(this.resourceUrl, owner, { observe: 'response' });
  }

  update(owner: IOwner): Observable<EntityResponseType> {
    return this.http.put<IOwner>(this.resourceUrl, owner, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOwner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOwner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
