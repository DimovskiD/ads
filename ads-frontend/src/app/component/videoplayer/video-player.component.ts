import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AdService} from '../../service/ad/ad.service';
import {Ad, AdType} from '../../model/ad/ad';

declare let videojs: any;

function showOverlayAd(player: any, imageAd: Ad) {
  player.overlay({
    content: 'Default overlay content',
    debug: true,
    overlays: [{
      content: '<img class = "img img-responsive" src ="' + 'http://localhost:8080/files/' + imageAd.fileName + '">',
      start: 0,
      end: imageAd.duration,
      align: 'center'
    }]});
}

function showLinearAd(player: any, ad: Ad) {
  player.ads.startLinearAdMode();
  // setTimeout(() => {
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
  poster = '//vjs.zencdn.net/v/oceans.png';
  video = '//vjs.zencdn.net/v/oceans.mp4';
  ad: Ad;
  imgAd: Ad;
  midrollPlayed = false;

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
      techOrder: ['html5']
    };
    this.vidObj = new videojs(this.vid.nativeElement, options, () => {
      videojs.log('Your player is ready!');
      const player = this.vidObj;
      player.ads();

      player.on('contentupdate', () => {
        // fetch ad inventory asynchronously, then ...
        player.trigger('adsready');
        player.log('ad ready!');
      });

      player.on('timeupdate', () => {
         if (Math.round(player.currentTime()) > 20 && !this.midrollPlayed) {
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
        showOverlayAd(player, img);
      });
      player.trigger('adsready');
      // player.on('timeupdate')
    });
  }

}


