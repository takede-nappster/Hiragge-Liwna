import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDecouverte } from 'app/shared/model/decouverte.model';

@Component({
  selector: 'jhi-decouverte-detail',
  templateUrl: './decouverte-detail.component.html',
})
export class DecouverteDetailComponent implements OnInit {
  decouverte: IDecouverte | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ decouverte }) => (this.decouverte = decouverte));
  }

  previousState(): void {
    window.history.back();
  }
}
