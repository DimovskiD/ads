import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { AdComponent } from './component/ad/ad.component';
import {RouterModule, Routes} from '@angular/router';
import { AdFormComponent } from './component/ad-form/ad-form.component';
import {FormsModule} from '@angular/forms';
import { VideoPlayerComponent } from './component/videoplayer/video-player.component';

const routes: Routes = [
  { path: '', component: AdComponent },
  { path: 'newAd', component: AdFormComponent },
  { path: 'ads/:ad', component: AdFormComponent },
  { path: 'videoPlayer', component: VideoPlayerComponent}
  ];

@NgModule({
  declarations: [
    AppComponent,
    AdComponent,
    AdFormComponent,
    VideoPlayerComponent
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
