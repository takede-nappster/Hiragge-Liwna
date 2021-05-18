import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDecouverte } from 'app/shared/model/decouverte.model';

type EntityResponseType = HttpResponse<IDecouverte>;
type EntityArrayResponseType = HttpResponse<IDecouverte[]>;

@Injectable({ providedIn: 'root' })
export class DecouverteService {
  public resourceUrl = SERVER_API_URL + 'api/decouvertes';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDecouverte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDecouverte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
