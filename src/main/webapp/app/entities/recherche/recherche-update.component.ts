import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecherche, Recherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';

@Component({
  selector: 'jhi-recherche-update',
  templateUrl: './recherche-update.component.html',
})
export class RechercheUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected rechercheService: RechercheService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recherche }) => {
      this.updateForm(recherche);
    });
  }

  updateForm(recherche: IRecherche): void {
    this.editForm.patchValue({
      id: recherche.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recherche = this.createFromForm();
    if (recherche.id !== undefined) {
      this.subscribeToSaveResponse(this.rechercheService.update(recherche));
    } else {
      this.subscribeToSaveResponse(this.rechercheService.create(recherche));
    }
  }

  private createFromForm(): IRecherche {
    return {
      ...new Recherche(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecherche>>): void {
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
}
