import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { SpecialtyDetailComponent } from 'app/entities/specialty/specialty-detail.component';
import { Specialty } from 'app/shared/model/specialty.model';

describe('Component Tests', () => {
  describe('Specialty Management Detail Component', () => {
    let comp: SpecialtyDetailComponent;
    let fixture: ComponentFixture<SpecialtyDetailComponent>;
    const route = ({ data: of({ specialty: new Specialty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [SpecialtyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SpecialtyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialtyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load specialty on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
