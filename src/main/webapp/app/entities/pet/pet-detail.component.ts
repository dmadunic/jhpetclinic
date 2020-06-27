import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPet } from 'app/shared/model/pet.model';

@Component({
  selector: 'jhi-pet-detail',
  templateUrl: './pet-detail.component.html',
})
export class PetDetailComponent implements OnInit {
  pet: IPet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pet }) => (this.pet = pet));
  }

  previousState(): void {
    window.history.back();
  }
}
