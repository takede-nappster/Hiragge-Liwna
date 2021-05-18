import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { DecouverteDetailComponent } from 'app/entities/decouverte/decouverte-detail.component';
import { Decouverte } from 'app/shared/model/decouverte.model';

describe('Component Tests', () => {
  describe('Decouverte Management Detail Component', () => {
    let comp: DecouverteDetailComponent;
    let fixture: ComponentFixture<DecouverteDetailComponent>;
    const route = ({ data: of({ decouverte: new Decouverte(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [DecouverteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DecouverteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DecouverteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load decouverte on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.decouverte).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
