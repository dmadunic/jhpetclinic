import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhpetclinicSharedModule } from 'app/shared/shared.module';
import { VisitComponent } from './visit.component';
import { VisitDetailComponent } from './visit-detail.component';
import { VisitUpdateComponent } from './visit-update.component';
import { VisitDeleteDialogComponent } from './visit-delete-dialog.component';
import { visitRoute } from './visit.route';

@NgModule({
  imports: [JhpetclinicSharedModule, RouterModule.forChild(visitRoute)],
  declarations: [VisitComponent, VisitDetailComponent, VisitUpdateComponent, VisitDeleteDialogComponent],
  entryComponents: [VisitDeleteDialogComponent],
})
export class JhpetclinicVisitModule {}
