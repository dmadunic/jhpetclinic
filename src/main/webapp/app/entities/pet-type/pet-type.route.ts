import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPetType, PetType } from 'app/shared/model/pet-type.model';
import { PetTypeService } from './pet-type.service';
import { PetTypeComponent } from './pet-type.component';
import { PetTypeDetailComponent } from './pet-type-detail.component';
import { PetTypeUpdateComponent } from './pet-type-update.component';

@Injectable({ providedIn: 'root' })
export class PetTypeResolve implements Resolve<IPetType> {
  constructor(private service: PetTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPetType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((petType: HttpResponse<PetType>) => {
          if (petType.body) {
            return of(petType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PetType());
  }
}

export const petTypeRoute: Routes = [
  {
    path: '',
    component: PetTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhpetclinicApp.petType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PetTypeDetailComponent,
    resolve: {
      petType: PetTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.petType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PetTypeUpdateComponent,
    resolve: {
      petType: PetTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.petType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PetTypeUpdateComponent,
    resolve: {
      petType: PetTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.petType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
