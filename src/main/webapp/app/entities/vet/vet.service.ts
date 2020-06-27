import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVet } from 'app/shared/model/vet.model';

type EntityResponseType = HttpResponse<IVet>;
type EntityArrayResponseType = HttpResponse<IVet[]>;

@Injectable({ providedIn: 'root' })
export class VetService {
  public resourceUrl = SERVER_API_URL + 'api/vets';

  constructor(protected http: HttpClient) {}

  create(vet: IVet): Observable<EntityResponseType> {
    return this.http.post<IVet>(this.resourceUrl, vet, { observe: 'response' });
  }

  update(vet: IVet): Observable<EntityResponseType> {
    return this.http.put<IVet>(this.resourceUrl, vet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
