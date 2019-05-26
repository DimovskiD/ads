import { Component, OnInit } from '@angular/core';
import { AfterViewInit, ViewChild, ElementRef } from '@angular/core';

// Declara a lib do videojs como externa ao angular
declare let videojs: any;

@Component({
  selector: 'app-videoplayer',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnInit, AfterViewInit  {


  title = 'Video player';
  vidObj: any;
  poster = '//vjs.zencdn.net/v/oceans.png';
  video = '//vjs.zencdn.net/v/oceans.mp4';
  @ViewChild('myvid') vid: ElementRef;
  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    const options = {
      controls: true,
      autoplay: false,
      preload: 'auto',
      techOrder: ['html5']
    };

    this.vidObj = new videojs(this.vid.nativeElement, options, function onPlayerReady() {
      videojs.log('Your player is ready!');
    });
  }

}


