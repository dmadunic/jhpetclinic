import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhpetclinicTestModule } from '../../../test.module';
import { VetDetailComponent } from 'app/entities/vet/vet-detail.component';
import { Vet } from 'app/shared/model/vet.model';

describe('Component Tests', () => {
  describe('Vet Management Detail Component', () => {
    let comp: VetDetailComponent;
    let fixture: ComponentFixture<VetDetailComponent>;
    const route = ({ data: of({ vet: new Vet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhpetclinicTestModule],
        declarations: [VetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
