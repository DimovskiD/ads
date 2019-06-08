import { Component, OnInit } from '@angular/core';
import { AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import {FileService} from '../../service/file/file.service';
import {log} from 'util';
import {AdService} from '../../service/ad/ad.service';
import {Ad} from '../../model/ad/ad';
declare let videojs: any;

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
  @ViewChild('myvid') vid: ElementRef;
  constructor(adService: AdService) {
    this.adService = adService;
    adService.findById(41).subscribe(data => {
      this.ad = data;
      console.log(this.ad.fileName);
    });

    adService.findById(42).subscribe(data => {
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
      preload: 'auto',
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

      player.on('readyforpreroll', () => {
        player.ads.startLinearAdMode();
        // play your linear ad content
        player.log('ad preroll ready!');
        player.src('http://localhost:8080/files/' + this.ad.fileName);
        player.overlay({
          content: 'Default overlay content',
          debug: true,
          overlays: [{
            content: '<img class = "img img-responsive" src ="' + 'http://localhost:8080/files/' + this.imgAd.fileName + '">',
            start: 0,
            end: 10,
            align: 'bottom'

          }]});
        // when all your linear ads have finished… do not confuse this with `ended`
        player.one('adended', () => {
          player.ads.endLinearAdMode();
        });
      });
      player.trigger('adsready');

    });
  }

}


