import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { SpecialtyUpdateComponent } from 'app/entities/specialty/specialty-update.component';
import { SpecialtyService } from 'app/entities/specialty/specialty.service';
import { Specialty } from 'app/shared/model/specialty.model';

describe('Component Tests', () => {
  describe('Specialty Management Update Component', () => {
    let comp: SpecialtyUpdateComponent;
    let fixture: ComponentFixture<SpecialtyUpdateComponent>;
    let service: SpecialtyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [SpecialtyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SpecialtyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialtyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialtyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Specialty(123);
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
        const entity = new Specialty();
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
