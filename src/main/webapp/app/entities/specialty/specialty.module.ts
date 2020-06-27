import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhpetclinicSharedModule } from 'app/shared/shared.module';
import { SpecialtyComponent } from './specialty.component';
import { SpecialtyDetailComponent } from './specialty-detail.component';
import { SpecialtyUpdateComponent } from './specialty-update.component';
import { SpecialtyDeleteDialogComponent } from './specialty-delete-dialog.component';
import { specialtyRoute } from './specialty.route';

@NgModule({
  imports: [JhpetclinicSharedModule, RouterModule.forChild(specialtyRoute)],
  declarations: [SpecialtyComponent, SpecialtyDetailComponent, SpecialtyUpdateComponent, SpecialtyDeleteDialogComponent],
  entryComponents: [SpecialtyDeleteDialogComponent],
})
export class JhpetclinicSpecialtyModule {}
