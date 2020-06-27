import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhpetclinicSharedModule } from 'app/shared/shared.module';
import { PetTypeComponent } from './pet-type.component';
import { PetTypeDetailComponent } from './pet-type-detail.component';
import { PetTypeUpdateComponent } from './pet-type-update.component';
import { PetTypeDeleteDialogComponent } from './pet-type-delete-dialog.component';
import { petTypeRoute } from './pet-type.route';

@NgModule({
  imports: [JhpetclinicSharedModule, RouterModule.forChild(petTypeRoute)],
  declarations: [PetTypeComponent, PetTypeDetailComponent, PetTypeUpdateComponent, PetTypeDeleteDialogComponent],
  entryComponents: [PetTypeDeleteDialogComponent],
})
export class JhpetclinicPetTypeModule {}
