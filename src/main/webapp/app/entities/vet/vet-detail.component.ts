import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVet } from 'app/shared/model/vet.model';

@Component({
  selector: 'jhi-vet-detail',
  templateUrl: './vet-detail.component.html',
})
export class VetDetailComponent implements OnInit {
  vet: IVet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vet }) => (this.vet = vet));
  }

  previousState(): void {
    window.history.back();
  }
}
