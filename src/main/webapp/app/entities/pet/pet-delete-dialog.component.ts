import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';

@Component({
  templateUrl: './pet-delete-dialog.component.html',
})
export class PetDeleteDialogComponent {
  pet?: IPet;

  constructor(protected petService: PetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.petService.delete(id).subscribe(() => {
      this.eventManager.broadcast('petListModification');
      this.activeModal.close();
    });
  }
}
