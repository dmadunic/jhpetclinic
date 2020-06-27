import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { PetUpdateComponent } from 'app/entities/pet/pet-update.component';
import { PetService } from 'app/entities/pet/pet.service';
import { Pet } from 'app/shared/model/pet.model';

describe('Component Tests', () => {
  describe('Pet Management Update Component', () => {
    let comp: PetUpdateComponent;
    let fixture: ComponentFixture<PetUpdateComponent>;
    let service: PetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [PetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pet(123);
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
        const entity = new Pet();
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
