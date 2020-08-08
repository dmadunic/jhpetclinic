import { Component } from '@angular/core';
import { BUILD_TIMESTAMP } from 'app/app.constants';
import * as moment from 'moment';
import { Moment } from 'moment';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
})
export class FooterComponent {
  buildTime: Moment;

  constructor() {
    this.buildTime = moment(+BUILD_TIMESTAMP!);
  }
}
