import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhpetclinicSharedModule } from 'app/shared/shared.module';
import { JhpetclinicCoreModule } from 'app/core/core.module';
import { JhpetclinicAppRoutingModule } from './app-routing.module';
import { JhpetclinicHomeModule } from './home/home.module';
import { JhpetclinicEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';
//import { LoadingBarRouterModule } from '@ngx-loading-bar/router';
import { LoadingBarModule } from '@ngx-loading-bar/core';

@NgModule({
  imports: [
    BrowserModule,
    JhpetclinicSharedModule,
    JhpetclinicCoreModule,
    JhpetclinicHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhpetclinicEntityModule,
    JhpetclinicAppRoutingModule,
    LoadingBarModule,
    LoadingBarHttpClientModule,
    //LoadingBarRouterModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class JhpetclinicAppModule {}
