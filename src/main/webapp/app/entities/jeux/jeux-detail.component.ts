import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IJeux } from 'app/shared/model/jeux.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-jeux-detail',
  templateUrl: './jeux-detail.component.html',
})
export class JeuxDetailComponent implements OnInit {
  jeux: IJeux | null = null;

  constructor(protected dataUtils: JhiDataUtils, 
              private accountService: AccountService,
              protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jeux }) => (this.jeux = jeux));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  downLoadFile(contentType = '', base64String: string): void {
    this.dataUtils.downloadFile(contentType, base64String, "setup.exe");
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  previousState(): void {
    window.history.back();
  }
}
