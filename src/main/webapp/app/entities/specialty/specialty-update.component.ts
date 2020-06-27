import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpecialty, Specialty } from 'app/shared/model/specialty.model';
import { SpecialtyService } from './specialty.service';

@Component({
  selector: 'jhi-specialty-update',
  templateUrl: './specialty-update.component.html',
})
export class SpecialtyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(80)]],
  });

  constructor(protected specialtyService: SpecialtyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialty }) => {
      this.updateForm(specialty);
    });
  }

  updateForm(specialty: ISpecialty): void {
    this.editForm.patchValue({
      id: specialty.id,
      name: specialty.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialty = this.createFromForm();
    if (specialty.id !== undefined) {
      this.subscribeToSaveResponse(this.specialtyService.update(specialty));
    } else {
      this.subscribeToSaveResponse(this.specialtyService.create(specialty));
    }
  }

  private createFromForm(): ISpecialty {
    return {
      ...new Specialty(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialty>>): void {
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
}
