import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from './owner.service';

@Component({
  templateUrl: './owner-delete-dialog.component.html',
})
export class OwnerDeleteDialogComponent {
  owner?: IOwner;

  constructor(protected ownerService: OwnerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ownerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ownerListModification');
      this.activeModal.close();
    });
  }
}
