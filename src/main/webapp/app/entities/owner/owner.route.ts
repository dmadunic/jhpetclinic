import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOwner, Owner } from 'app/shared/model/owner.model';
import { OwnerService } from './owner.service';
import { OwnerComponent } from './owner.component';
import { OwnerDetailComponent } from './owner-detail.component';
import { OwnerUpdateComponent } from './owner-update.component';

@Injectable({ providedIn: 'root' })
export class OwnerResolve implements Resolve<IOwner> {
  constructor(private service: OwnerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOwner> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((owner: HttpResponse<Owner>) => {
          if (owner.body) {
            return of(owner.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Owner());
  }
}

export const ownerRoute: Routes = [
  {
    path: '',
    component: OwnerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhpetclinicApp.owner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OwnerDetailComponent,
    resolve: {
      owner: OwnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.owner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OwnerUpdateComponent,
    resolve: {
      owner: OwnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.owner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OwnerUpdateComponent,
    resolve: {
      owner: OwnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.owner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
