import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pet-type',
        loadChildren: () => import('./pet-type/pet-type.module').then(m => m.JhpetclinicPetTypeModule),
      },
      {
        path: 'specialty',
        loadChildren: () => import('./specialty/specialty.module').then(m => m.JhpetclinicSpecialtyModule),
      },
      {
        path: 'vet',
        loadChildren: () => import('./vet/vet.module').then(m => m.JhpetclinicVetModule),
      },
      {
        path: 'owner',
        loadChildren: () => import('./owner/owner.module').then(m => m.JhpetclinicOwnerModule),
      },
      {
        path: 'pet',
        loadChildren: () => import('./pet/pet.module').then(m => m.JhpetclinicPetModule),
      },
      {
        path: 'visit',
        loadChildren: () => import('./visit/visit.module').then(m => m.JhpetclinicVisitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhpetclinicEntityModule {}
