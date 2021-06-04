import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJeux, Jeux } from 'app/shared/model/jeux.model';
import { JeuxService } from './jeux.service';
import { JeuxComponent } from './jeux.component';
import { JeuxDetailComponent } from './jeux-detail.component';
import { JeuxUpdateComponent } from './jeux-update.component';

@Injectable({ providedIn: 'root' })
export class JeuxResolve implements Resolve<IJeux> {
  constructor(private service: JeuxService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJeux> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jeux: HttpResponse<Jeux>) => {
          if (jeux.body) {
            return of(jeux.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Jeux());
  }
}

export const jeuxRoute: Routes = [
  {
    path: '',
    component: JeuxComponent,
    data: {
      //authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testApp.jeux.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JeuxDetailComponent,
    resolve: {
      jeux: JeuxResolve,
    },
    data: {
      //authorities: [Authority.USER],
      pageTitle: 'testApp.jeux.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JeuxUpdateComponent,
    resolve: {
      jeux: JeuxResolve,
    },
    data: {
      //authorities: [Authority.USER],
      pageTitle: 'testApp.jeux.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JeuxUpdateComponent,
    resolve: {
      jeux: JeuxResolve,
    },
    data: {
    //  authorities: [Authority.USER],
      pageTitle: 'testApp.jeux.home.title',
    },
    //canActivate: [UserRouteAccessService],
  },
];
