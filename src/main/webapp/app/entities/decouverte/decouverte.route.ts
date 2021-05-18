import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDecouverte, Decouverte } from 'app/shared/model/decouverte.model';
import { DecouverteService } from './decouverte.service';
import { DecouverteComponent } from './decouverte.component';
import { DecouverteDetailComponent } from './decouverte-detail.component';

@Injectable({ providedIn: 'root' })
export class DecouverteResolve implements Resolve<IDecouverte> {
  constructor(private service: DecouverteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDecouverte> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((decouverte: HttpResponse<Decouverte>) => {
          if (decouverte.body) {
            return of(decouverte.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Decouverte());
  }
}

export const decouverteRoute: Routes = [
  {
    path: '',
    component: DecouverteComponent,
    data: {
      //authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testApp.decouverte.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DecouverteDetailComponent,
    resolve: {
      decouverte: DecouverteResolve,
    },
    data: {
      //authorities: [Authority.USER],
      pageTitle: 'testApp.decouverte.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
];
