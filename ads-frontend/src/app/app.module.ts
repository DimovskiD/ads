import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { AdComponent } from './component/ad/ad.component';
import {RouterModule, Routes} from '@angular/router';
import { AdFormComponent } from './component/ad-form/ad-form.component';
import {FormsModule} from '@angular/forms';

const routes: Routes = [
  { path: '', component: AdComponent },
  { path: 'newAd', component: AdFormComponent }

  // { path: 'adduser', component: UserFormComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    AdComponent,
    AdFormComponent
  ],
  imports: [
    BrowserModule,
    [RouterModule.forRoot(routes)],
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [RouterModule]

})
export class AppModule { }
