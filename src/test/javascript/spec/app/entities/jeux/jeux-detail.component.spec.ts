import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { JeuxDetailComponent } from 'app/entities/jeux/jeux-detail.component';
import { Jeux } from 'app/shared/model/jeux.model';

describe('Component Tests', () => {
  describe('Jeux Management Detail Component', () => {
    let comp: JeuxDetailComponent;
    let fixture: ComponentFixture<JeuxDetailComponent>;
    const route = ({ data: of({ jeux: new Jeux(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [JeuxDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JeuxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JeuxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jeux on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jeux).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
