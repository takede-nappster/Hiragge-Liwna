import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompetition, Competition } from 'app/shared/model/competition.model';
import { CompetitionService } from './competition.service';

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html',
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;
  dateDebutDp: any;
  dateFinDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    description: [],
    dateDebut: [],
    dateFin: [],
    nombreParticipant: [],
  });

  constructor(protected competitionService: CompetitionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);
    });
  }

  updateForm(competition: ICompetition): void {
    this.editForm.patchValue({
      id: competition.id,
      nom: competition.nom,
      description: competition.description,
      dateDebut: competition.dateDebut,
      dateFin: competition.dateFin,
      nombreParticipant: competition.nombreParticipant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competition = this.createFromForm();
    if (competition.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionService.update(competition));
    } else {
      this.subscribeToSaveResponse(this.competitionService.create(competition));
    }
  }

  private createFromForm(): ICompetition {
    return {
      ...new Competition(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      nombreParticipant: this.editForm.get(['nombreParticipant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetition>>): void {
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
