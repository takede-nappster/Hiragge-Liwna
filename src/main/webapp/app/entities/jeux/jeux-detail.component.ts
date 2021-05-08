import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJeux } from 'app/shared/model/jeux.model';

@Component({
  selector: 'jhi-jeux-detail',
  templateUrl: './jeux-detail.component.html',
})
export class JeuxDetailComponent implements OnInit {
  jeux: IJeux | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jeux }) => (this.jeux = jeux));
  }

  previousState(): void {
    window.history.back();
  }
}
