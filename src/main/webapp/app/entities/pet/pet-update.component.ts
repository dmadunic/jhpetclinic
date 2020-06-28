import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, combineLatest } from 'rxjs';

import { IPet, Pet } from 'app/shared/model/pet.model';
import { PetService } from './pet.service';
import { IPetType } from 'app/shared/model/pet-type.model';
import { PetTypeService } from 'app/entities/pet-type/pet-type.service';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner/owner.service';

type SelectableEntity = IPetType | IOwner;

@Component({
  selector: 'jhi-pet-update',
  templateUrl: './pet-update.component.html',
})
export class PetUpdateComponent implements OnInit {
  isSaving = false;
  pettypes: IPetType[] = [];
  owners: IOwner[] = [];
  birthDateDp: any;
  ownerId: number | null = null;
  initialized = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(30)]],
    birthDate: [],
    typeId: [],
    ownerId: [],
  });

  constructor(
    protected petService: PetService,
    protected petTypeService: PetTypeService,
    protected ownerService: OwnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const obsComb = combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data, qparams) => ({ data, qparams }));
    obsComb.subscribe(ap => {
      if (!this.initialized) {
        this.updateForm(ap.data.pet);
        if (ap.qparams.get('ownerId')) {
          this.ownerId = Number(ap.qparams.get('ownerId'));
          this.editForm.patchValue({ ownerId: this.ownerId });
        }
        this.petTypeService.query().subscribe((res: HttpResponse<IPetType[]>) => (this.pettypes = res.body || []));
        this.ownerService.query().subscribe((res: HttpResponse<IOwner[]>) => (this.owners = res.body || []));
        this.initialized = true;
      }
    });
  }

  updateForm(pet: IPet): void {
    this.editForm.patchValue({
      id: pet.id,
      name: pet.name,
      birthDate: pet.birthDate,
      typeId: pet.typeId,
      ownerId: pet.ownerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pet = this.createFromForm();
    if (pet.id !== undefined) {
      this.subscribeToSaveResponse(this.petService.update(pet));
    } else {
      this.subscribeToSaveResponse(this.petService.create(pet));
    }
  }

  private createFromForm(): IPet {
    return {
      ...new Pet(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
