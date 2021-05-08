import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatch, Match } from 'app/shared/model/match.model';
import { MatchService } from './match.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';

@Component({
  selector: 'jhi-match-update',
  templateUrl: './match-update.component.html',
})
export class MatchUpdateComponent implements OnInit {
  isSaving = false;
  competitions: ICompetition[] = [];
  dateMatchDp: any;

  editForm = this.fb.group({
    id: [],
    dateMatch: [],
    competitionId: [],
  });

  constructor(
    protected matchService: MatchService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ match }) => {
      this.updateForm(match);

      this.competitionService.query().subscribe((res: HttpResponse<ICompetition[]>) => (this.competitions = res.body || []));
    });
  }

  updateForm(match: IMatch): void {
    this.editForm.patchValue({
      id: match.id,
      dateMatch: match.dateMatch,
      competitionId: match.competitionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const match = this.createFromForm();
    if (match.id !== undefined) {
      this.subscribeToSaveResponse(this.matchService.update(match));
    } else {
      this.subscribeToSaveResponse(this.matchService.create(match));
    }
  }

  private createFromForm(): IMatch {
    return {
      ...new Match(),
      id: this.editForm.get(['id'])!.value,
      dateMatch: this.editForm.get(['dateMatch'])!.value,
      competitionId: this.editForm.get(['competitionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatch>>): void {
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
