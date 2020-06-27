import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhpetclinicSharedModule } from 'app/shared/shared.module';
import { VetComponent } from './vet.component';
import { VetDetailComponent } from './vet-detail.component';
import { VetUpdateComponent } from './vet-update.component';
import { VetDeleteDialogComponent } from './vet-delete-dialog.component';
import { vetRoute } from './vet.route';

@NgModule({
  imports: [JhpetclinicSharedModule, RouterModule.forChild(vetRoute)],
  declarations: [VetComponent, VetDetailComponent, VetUpdateComponent, VetDeleteDialogComponent],
  entryComponents: [VetDeleteDialogComponent],
})
export class JhpetclinicVetModule {}
