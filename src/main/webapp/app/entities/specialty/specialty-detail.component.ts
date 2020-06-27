import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialty } from 'app/shared/model/specialty.model';

@Component({
  selector: 'jhi-specialty-detail',
  templateUrl: './specialty-detail.component.html',
})
export class SpecialtyDetailComponent implements OnInit {
  specialty: ISpecialty | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialty }) => (this.specialty = specialty));
  }

  previousState(): void {
    window.history.back();
  }
}
