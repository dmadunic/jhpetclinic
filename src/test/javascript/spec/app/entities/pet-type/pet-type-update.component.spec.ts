import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { PetTypeUpdateComponent } from 'app/entities/pet-type/pet-type-update.component';
import { PetTypeService } from 'app/entities/pet-type/pet-type.service';
import { PetType } from 'app/shared/model/pet-type.model';

describe('Component Tests', () => {
  describe('PetType Management Update Component', () => {
    let comp: PetTypeUpdateComponent;
    let fixture: ComponentFixture<PetTypeUpdateComponent>;
    let service: PetTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [PetTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PetTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PetTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PetTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PetType(123);
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
        const entity = new PetType();
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
