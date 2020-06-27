import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPetType } from 'app/shared/model/pet-type.model';
import { PetTypeService } from './pet-type.service';

@Component({
  templateUrl: './pet-type-delete-dialog.component.html',
})
export class PetTypeDeleteDialogComponent {
  petType?: IPetType;

  constructor(protected petTypeService: PetTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.petTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('petTypeListModification');
      this.activeModal.close();
    });
  }
}
