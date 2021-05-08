import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { JeuxUpdateComponent } from 'app/entities/jeux/jeux-update.component';
import { JeuxService } from 'app/entities/jeux/jeux.service';
import { Jeux } from 'app/shared/model/jeux.model';

describe('Component Tests', () => {
  describe('Jeux Management Update Component', () => {
    let comp: JeuxUpdateComponent;
    let fixture: ComponentFixture<JeuxUpdateComponent>;
    let service: JeuxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [JeuxUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JeuxUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JeuxUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JeuxService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jeux(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jeux();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
