import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpecialty } from 'app/shared/model/specialty.model';

type EntityResponseType = HttpResponse<ISpecialty>;
type EntityArrayResponseType = HttpResponse<ISpecialty[]>;

@Injectable({ providedIn: 'root' })
export class SpecialtyService {
  public resourceUrl = SERVER_API_URL + 'api/specialties';

  constructor(protected http: HttpClient) {}

  create(specialty: ISpecialty): Observable<EntityResponseType> {
    return this.http.post<ISpecialty>(this.resourceUrl, specialty, { observe: 'response' });
  }

  update(specialty: ISpecialty): Observable<EntityResponseType> {
    return this.http.put<ISpecialty>(this.resourceUrl, specialty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
