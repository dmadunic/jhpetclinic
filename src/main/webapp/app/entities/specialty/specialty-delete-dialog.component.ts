import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialty } from 'app/shared/model/specialty.model';
import { SpecialtyService } from './specialty.service';

@Component({
  templateUrl: './specialty-delete-dialog.component.html',
})
export class SpecialtyDeleteDialogComponent {
  specialty?: ISpecialty;

  constructor(protected specialtyService: SpecialtyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specialtyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('specialtyListModification');
      this.activeModal.close();
    });
  }
}
