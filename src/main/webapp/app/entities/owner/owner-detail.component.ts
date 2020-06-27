import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOwner } from 'app/shared/model/owner.model';

@Component({
  selector: 'jhi-owner-detail',
  templateUrl: './owner-detail.component.html',
})
export class OwnerDetailComponent implements OnInit {
  owner: IOwner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ owner }) => (this.owner = owner));
  }

  previousState(): void {
    window.history.back();
  }
}
