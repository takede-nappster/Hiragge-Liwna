import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJeux } from 'app/shared/model/jeux.model';

type EntityResponseType = HttpResponse<IJeux>;
type EntityArrayResponseType = HttpResponse<IJeux[]>;

@Injectable({ providedIn: 'root' })
export class JeuxService {
  public resourceUrl = SERVER_API_URL + 'api/jeuxes';

  constructor(protected http: HttpClient) {}

  create(jeux: IJeux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jeux);
    return this.http
      .post<IJeux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jeux: IJeux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jeux);
    return this.http
      .put<IJeux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJeux>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJeux[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jeux: IJeux): IJeux {
    const copy: IJeux = Object.assign({}, jeux, {
      dateCreation: jeux.dateCreation && jeux.dateCreation.isValid() ? jeux.dateCreation.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jeux: IJeux) => {
        jeux.dateCreation = jeux.dateCreation ? moment(jeux.dateCreation) : undefined;
      });
    }
    return res;
  }
}
