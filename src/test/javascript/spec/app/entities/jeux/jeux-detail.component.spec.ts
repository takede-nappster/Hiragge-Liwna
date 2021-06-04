import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TestTestModule } from '../../../test.module';
import { JeuxDetailComponent } from 'app/entities/jeux/jeux-detail.component';
import { Jeux } from 'app/shared/model/jeux.model';

describe('Component Tests', () => {
  describe('Jeux Management Detail Component', () => {
    let comp: JeuxDetailComponent;
    let fixture: ComponentFixture<JeuxDetailComponent>;
    let dataUtils: JhiDataUtils;
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
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load jeux on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jeux).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
