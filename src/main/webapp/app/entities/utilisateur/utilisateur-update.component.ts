import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUtilisateur, Utilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';
import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from 'app/entities/match/match.service';

@Component({
  selector: 'jhi-utilisateur-update',
  templateUrl: './utilisateur-update.component.html',
})
export class UtilisateurUpdateComponent implements OnInit {
  isSaving = false;
  matches: IMatch[] = [];

  editForm = this.fb.group({
    id: [],
    userType: [],
    matches: [],
  });

  constructor(
    protected utilisateurService: UtilisateurService,
    protected matchService: MatchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ utilisateur }) => {
      this.updateForm(utilisateur);

      this.matchService.query().subscribe((res: HttpResponse<IMatch[]>) => (this.matches = res.body || []));
    });
  }

  updateForm(utilisateur: IUtilisateur): void {
    this.editForm.patchValue({
      id: utilisateur.id,
      userType: utilisateur.userType,
      matches: utilisateur.matches,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const utilisateur = this.createFromForm();
    if (utilisateur.id !== undefined) {
      this.subscribeToSaveResponse(this.utilisateurService.update(utilisateur));
    } else {
      this.subscribeToSaveResponse(this.utilisateurService.create(utilisateur));
    }
  }

  private createFromForm(): IUtilisateur {
    return {
      ...new Utilisateur(),
      id: this.editForm.get(['id'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      matches: this.editForm.get(['matches'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateur>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMatch): any {
    return item.id;
  }

  getSelected(selectedVals: IMatch[], option: IMatch): IMatch {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
