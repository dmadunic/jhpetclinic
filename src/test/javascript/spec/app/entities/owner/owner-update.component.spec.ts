import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { OwnerUpdateComponent } from 'app/entities/owner/owner-update.component';
import { OwnerService } from 'app/entities/owner/owner.service';
import { Owner } from 'app/shared/model/owner.model';

describe('Component Tests', () => {
  describe('Owner Management Update Component', () => {
    let comp: OwnerUpdateComponent;
    let fixture: ComponentFixture<OwnerUpdateComponent>;
    let service: OwnerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [OwnerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OwnerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OwnerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Owner(123);
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
        const entity = new Owner();
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
