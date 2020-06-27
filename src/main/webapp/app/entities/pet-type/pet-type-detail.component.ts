import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPetType } from 'app/shared/model/pet-type.model';

@Component({
  selector: 'jhi-pet-type-detail',
  templateUrl: './pet-type-detail.component.html',
})
export class PetTypeDetailComponent implements OnInit {
  petType: IPetType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ petType }) => (this.petType = petType));
  }

  previousState(): void {
    window.history.back();
  }
}
