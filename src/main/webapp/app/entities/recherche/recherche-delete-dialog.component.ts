import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';

@Component({
  templateUrl: './recherche-delete-dialog.component.html',
})
export class RechercheDeleteDialogComponent {
  recherche?: IRecherche;

  constructor(protected rechercheService: RechercheService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rechercheService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rechercheListModification');
      this.activeModal.close();
    });
  }
}
