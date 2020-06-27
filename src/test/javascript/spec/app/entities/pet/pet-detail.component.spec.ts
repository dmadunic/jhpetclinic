import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { PetDetailComponent } from 'app/entities/pet/pet-detail.component';
import { Pet } from 'app/shared/model/pet.model';

describe('Component Tests', () => {
  describe('Pet Management Detail Component', () => {
    let comp: PetDetailComponent;
    let fixture: ComponentFixture<PetDetailComponent>;
    const route = ({ data: of({ pet: new Pet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [PetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
