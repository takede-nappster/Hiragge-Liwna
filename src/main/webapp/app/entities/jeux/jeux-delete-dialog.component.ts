import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJeux } from 'app/shared/model/jeux.model';
import { JeuxService } from './jeux.service';

@Component({
  templateUrl: './jeux-delete-dialog.component.html',
})
export class JeuxDeleteDialogComponent {
  jeux?: IJeux;

  constructor(protected jeuxService: JeuxService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jeuxService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jeuxListModification');
      this.activeModal.close();
    });
  }
}
