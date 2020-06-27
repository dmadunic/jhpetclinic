import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpecialty, Specialty } from 'app/shared/model/specialty.model';
import { SpecialtyService } from './specialty.service';
import { SpecialtyComponent } from './specialty.component';
import { SpecialtyDetailComponent } from './specialty-detail.component';
import { SpecialtyUpdateComponent } from './specialty-update.component';

@Injectable({ providedIn: 'root' })
export class SpecialtyResolve implements Resolve<ISpecialty> {
  constructor(private service: SpecialtyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialty> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((specialty: HttpResponse<Specialty>) => {
          if (specialty.body) {
            return of(specialty.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Specialty());
  }
}

export const specialtyRoute: Routes = [
  {
    path: '',
    component: SpecialtyComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhpetclinicApp.specialty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecialtyDetailComponent,
    resolve: {
      specialty: SpecialtyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.specialty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecialtyUpdateComponent,
    resolve: {
      specialty: SpecialtyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.specialty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecialtyUpdateComponent,
    resolve: {
      specialty: SpecialtyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhpetclinicApp.specialty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
