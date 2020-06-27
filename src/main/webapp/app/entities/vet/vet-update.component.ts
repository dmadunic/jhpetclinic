import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVet, Vet } from 'app/shared/model/vet.model';
import { VetService } from './vet.service';
import { ISpecialty } from 'app/shared/model/specialty.model';
import { SpecialtyService } from 'app/entities/specialty/specialty.service';

@Component({
  selector: 'jhi-vet-update',
  templateUrl: './vet-update.component.html',
})
export class VetUpdateComponent implements OnInit {
  isSaving = false;
  specialties: ISpecialty[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required, Validators.maxLength(30)]],
    lastName: [null, [Validators.required, Validators.maxLength(30)]],
    specialties: [],
  });

  constructor(
    protected vetService: VetService,
    protected specialtyService: SpecialtyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vet }) => {
      this.updateForm(vet);

      this.specialtyService.query().subscribe((res: HttpResponse<ISpecialty[]>) => (this.specialties = res.body || []));
    });
  }

  updateForm(vet: IVet): void {
    this.editForm.patchValue({
      id: vet.id,
      firstName: vet.firstName,
      lastName: vet.lastName,
      specialties: vet.specialties,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vet = this.createFromForm();
    if (vet.id !== undefined) {
      this.subscribeToSaveResponse(this.vetService.update(vet));
    } else {
      this.subscribeToSaveResponse(this.vetService.create(vet));
    }
  }

  private createFromForm(): IVet {
    return {
      ...new Vet(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      specialties: this.editForm.get(['specialties'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVet>>): void {
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

  trackById(index: number, item: ISpecialty): any {
    return item.id;
  }

  getSelected(selectedVals: ISpecialty[], option: ISpecialty): ISpecialty {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
