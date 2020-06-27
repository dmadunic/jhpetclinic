import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPet, Pet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';
import { PetComponent } from './pet.component';
import { PetDetailComponent } from './pet-detail.component';
import { PetUpdateComponent } from './pet-update.component';

@Injectable({ providedIn: 'root' })
export class PetResolve implements Resolve<IPet> {
  constructor(private service: PetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pet: HttpResponse<Pet>) => {
          if (pet.body) {
            return of(pet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pet());
  }
}

export const petRoute: Routes = [
  {
    path: '',
    component: PetComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhpetclinicApp.pet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PetDetailComponent,
    resolve: {
      pet: PetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.pet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PetUpdateComponent,
    resolve: {
      pet: PetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.pet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PetUpdateComponent,
    resolve: {
      pet: PetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.pet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
