import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJeux, Jeux } from 'app/shared/model/jeux.model';
import { JeuxService } from './jeux.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-jeux-update',
  templateUrl: './jeux-update.component.html',
})
export class JeuxUpdateComponent implements OnInit {
  isSaving = false;
  competitions: ICompetition[] = [];
  dateCreationDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    description: [],
    dateCreation: [],
    concepteur: [],
    prix: [],
    meilleurScore: [],
    lienTelechargement: [],
    lienJouer: [],
    competitionId: [],
  });

  constructor(
    protected jeuxService: JeuxService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jeux }) => {
      this.updateForm(jeux);

      this.competitionService.query().subscribe((res: HttpResponse<ICompetition[]>) => (this.competitions = res.body || []));
    });
  }

  updateForm(jeux: IJeux): void {
    this.editForm.patchValue({
      id: jeux.id,
      nom: jeux.nom,
      description: jeux.description,
      dateCreation: jeux.dateCreation,
      concepteur: jeux.concepteur,
      prix: jeux.prix,
      meilleurScore: jeux.meilleurScore,
      lienTelechargement: jeux.lienTelechargement,
      lienJouer: jeux.lienJouer,
      competitionId: jeux.competitionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jeux = this.createFromForm();
    if (jeux.id !== undefined) {
      this.subscribeToSaveResponse(this.jeuxService.update(jeux));
    } else {
      this.subscribeToSaveResponse(this.jeuxService.create(jeux));
    }
  }

  private createFromForm(): IJeux {
    return {
      ...new Jeux(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      concepteur: this.editForm.get(['concepteur'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      meilleurScore: this.editForm.get(['meilleurScore'])!.value,
      lienTelechargement: this.editForm.get(['lienTelechargement'])!.value,
      lienJouer: this.editForm.get(['lienJouer'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJeux>>): void {
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

  trackById(index: number, item: ICompetition): any {
    return item.id;
  }
}
