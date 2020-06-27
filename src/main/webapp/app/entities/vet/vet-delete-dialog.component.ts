import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVet } from 'app/shared/model/vet.model';
import { VetService } from './vet.service';

@Component({
  templateUrl: './vet-delete-dialog.component.html',
})
export class VetDeleteDialogComponent {
  vet?: IVet;

  constructor(protected vetService: VetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vetListModification');
      this.activeModal.close();
    });
  }
}
