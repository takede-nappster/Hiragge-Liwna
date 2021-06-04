import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IJeux, Jeux } from 'app/shared/model/jeux.model';
import { JeuxService } from './jeux.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
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
    dateCreation: [],
    concepteur: [],
    prix: [],
    meilleurScore: [],
    lienJouer: [],
    logo: [],
    logoContentType: [],
    setupFile: [],
    setupFileContentType: [],
    description: [null, [Validators.required, Validators.maxLength(10000)]],
    competitionId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jeuxService: JeuxService,
    protected competitionService: CompetitionService,
    protected elementRef: ElementRef,
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
      dateCreation: jeux.dateCreation,
      concepteur: jeux.concepteur,
      prix: jeux.prix,
      meilleurScore: jeux.meilleurScore,
      lienJouer: jeux.lienJouer,
      logo: jeux.logo,
      logoContentType: jeux.logoContentType,
      setupFile: jeux.setupFile,
      setupFileContentType: jeux.setupFileContentType,
      description: jeux.description,
      competitionId: jeux.competitionId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('testApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      concepteur: this.editForm.get(['concepteur'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      meilleurScore: this.editForm.get(['meilleurScore'])!.value,
      lienJouer: this.editForm.get(['lienJouer'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      setupFileContentType: this.editForm.get(['setupFileContentType'])!.value,
      setupFile: this.editForm.get(['setupFile'])!.value,
      description: this.editForm.get(['description'])!.value,
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
