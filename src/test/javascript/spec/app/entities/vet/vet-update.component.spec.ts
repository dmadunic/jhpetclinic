import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { VetUpdateComponent } from 'app/entities/vet/vet-update.component';
import { VetService } from 'app/entities/vet/vet.service';
import { Vet } from 'app/shared/model/vet.model';

describe('Component Tests', () => {
  describe('Vet Management Update Component', () => {
    let comp: VetUpdateComponent;
    let fixture: ComponentFixture<VetUpdateComponent>;
    let service: VetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [VetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vet(123);
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
        const entity = new Vet();
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
