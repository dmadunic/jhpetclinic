import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVet, Vet } from 'app/shared/model/vet.model';
import { VetService } from './vet.service';
import { VetComponent } from './vet.component';
import { VetDetailComponent } from './vet-detail.component';
import { VetUpdateComponent } from './vet-update.component';

@Injectable({ providedIn: 'root' })
export class VetResolve implements Resolve<IVet> {
  constructor(private service: VetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vet: HttpResponse<Vet>) => {
          if (vet.body) {
            return of(vet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vet());
  }
}

export const vetRoute: Routes = [
  {
    path: '',
    component: VetComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhpetclinicApp.vet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VetDetailComponent,
    resolve: {
      vet: VetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.vet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VetUpdateComponent,
    resolve: {
      vet: VetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.vet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VetUpdateComponent,
    resolve: {
      vet: VetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.vet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
