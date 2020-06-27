import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { PetTypeDetailComponent } from 'app/entities/pet-type/pet-type-detail.component';
import { PetType } from 'app/shared/model/pet-type.model';

describe('Component Tests', () => {
  describe('PetType Management Detail Component', () => {
    let comp: PetTypeDetailComponent;
    let fixture: ComponentFixture<PetTypeDetailComponent>;
    const route = ({ data: of({ petType: new PetType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [PetTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PetTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PetTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load petType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.petType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
