import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { RechercheDetailComponent } from 'app/entities/recherche/recherche-detail.component';
import { Recherche } from 'app/shared/model/recherche.model';

describe('Component Tests', () => {
  describe('Recherche Management Detail Component', () => {
    let comp: RechercheDetailComponent;
    let fixture: ComponentFixture<RechercheDetailComponent>;
    const route = ({ data: of({ recherche: new Recherche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [RechercheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RechercheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RechercheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recherche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recherche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
