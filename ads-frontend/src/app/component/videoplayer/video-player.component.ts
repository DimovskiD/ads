import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AdService} from '../../service/ad/ad.service';
import {Ad, AdType} from '../../model/ad/ad';
import {Log} from '@angular/core/testing/src/logger';

declare let videojs: any;

// call this function with reference to the videoJs player and ad object to show overlay ad
// the ad will be shown from the start of the video for as many seconds as specified in the ad.duration field
function showOverlayAd(player: any, imageAd: Ad, duration: number) {
  console.log('imageAd ' + Math.round(duration * (imageAd.whenToShow / 100)));
  player.overlay({
    content: 'Default overlay content',
    debug: true,
    overlays: [{
      content: '<img class = "img img-responsive" src ="' + 'http://localhost:8080/files/' + imageAd.fileName + '">',
      start: Math.round(duration * (imageAd.whenToShow / 100)),
      end: Math.round(duration * (imageAd.whenToShow / 100)) + imageAd.duration,
      align: 'center'
    }]});
}

function showLinearAd(player: any, ad: Ad) {
  console.log('videoAd ' + ad.whenToShow);

  if (ad === undefined) {return; }
  player.ads.startLinearAdMode();
  // setTimeout(() => { //uncomment this if you want the linear ad to last for as many seconds as specified in ad.duration field
  //   player.trigger('adended');
  // }, this.ad.duration * 1000);
  // play your linear ad content
  player.src('http://localhost:8080/files/' + ad.fileName);

}

@Component({
  selector: 'app-videoplayer',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnInit, AfterViewInit  {

  adService: AdService;
  title = 'Video player';
  vidObj: any;
  poster = '//vjs.zencdn.net/v/oceans.png'; // default video value, should be replaced with dynamic link to video
  video = '//vjs.zencdn.net/v/oceans.mp4';  // default poster size, should be replaced with dynamic link to poster
  ad: Ad;  // reference to linear ad
  imgAd: Ad; // reference to overlay ad
  midrollPlayed = false; // true if midroll ad was played - used to show midroll only once, remove to show it more than once

  @ViewChild('myvid') vid: ElementRef;
  constructor(adService: AdService) {
    this.adService = adService;
    adService.getRandomAdByType(AdType.LINEAR).subscribe(data => {
      this.ad = data;
      console.log(this.ad.fileName);
    });
    adService.getRandomAdByType(AdType.OVERLAY).subscribe(data => {
      this.imgAd = data;
      console.log(this.ad.fileName);
    });

  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    const options = {
      controls: true,
      autoplay: false,
      preload: true,
      techOrder: ['html5']
    };
    let duration = 0;
    this.vidObj = new videojs(this.vid.nativeElement, options, () => {
      videojs.log('Your player is ready!');
      const player = this.vidObj;
      player.ads();
      player.on('loadedmetadata', () => {
        duration = player.duration();
      });
      player.on('contentupdate', () => {
        // fetch ad inventory asynchronously, then ...
        player.trigger('adsready');
        player.log('ad ready!');
      });

      player.on('timeupdate', () => {
         if (Math.round(player.currentTime()) >= (duration * (this.ad.whenToShow / 100)) && !this.midrollPlayed) {
            player.trigger('readyformidroll');
            this.midrollPlayed = true;
         }
      });

      player.on('readyforpreroll', () => {
        player.log('ad preroll ready!');
        showLinearAd(player, this.ad);
      });

      player.on('readyformidroll', () => {
        player.log('ad midroll ready!');
        showLinearAd(player, this.ad);
      });
      player.on('readyforpostroll', () => {
        player.log('ad postroll ready!');
        showLinearAd(player, this.ad);
      });
      // when all your linear ads have finished… do not confuse this with `ended`
      player.on('adended', () => {
        player.ads.endLinearAdMode();
        this.adService.getRandomAdByType(AdType.LINEAR).subscribe(data => {
          this.ad = data;
          console.log(this.ad.fileName);
        }); // load new ad for next ad showing
        const img = this.imgAd;
        showOverlayAd(player, img, duration);
      });
      player.trigger('adsready');
    });
  }

}


