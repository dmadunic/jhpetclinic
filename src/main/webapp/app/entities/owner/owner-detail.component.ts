import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { MAX_PETS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IOwner } from 'app/shared/model/owner.model';
import { IPet } from 'app/shared/model/pet.model';
import { PetService } from '../pet/pet.service';

@Component({
  selector: 'jhi-owner-detail',
  templateUrl: './owner-detail.component.html',
})
export class OwnerDetailComponent implements OnInit {
  owner: IOwner | null = null;
  pets: IPet[] = [];

  constructor(protected activatedRoute: ActivatedRoute, protected petService: PetService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.owner = owner;
      const req = {
        page: 0,
        size: MAX_PETS_PER_PAGE,
        sort: this.petsSort(),
      };
      req['ownerId.equals'] = this.owner!.id;

      this.petService.query(req).subscribe((res: HttpResponse<IPet[]>) => (this.pets = res.body || []));
    });
  }

  protected petsSort(): string[] {
    const result = ['birthDate,desc'];
    return result;
  }

  previousState(): void {
    window.history.back();
  }
}
