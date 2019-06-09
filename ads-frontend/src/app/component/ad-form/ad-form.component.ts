import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AdService} from '../../service/ad/ad.service';
import {Ad, AdType, Category} from '../../model/ad/ad';
import {FileService} from '../../service/file/file.service';
import {log} from 'util';


declare let videojs: any;


@Component({
  selector: 'app-ad-form',
  templateUrl: './ad-form.component.html',
  styleUrls: ['./ad-form.component.css']
})
export class AdFormComponent implements OnInit {

  ad: Ad;
  name: string;
  fail: boolean;
  file: File;
  categories = Category;
  adTypes = AdType;
  existingAd = false;
  vidObj: any;
  @ViewChild('myvid') vid: ElementRef;
  videoHidden = true;
  imageHidden = true;


  constructor(private route: ActivatedRoute, private router: Router, private adService: AdService, private fileService: FileService) {
    this.ad = new Ad();
    this.fail = false;
  }
  ngOnInit() {
    if (this.route.snapshot.paramMap.has('ad')) { // if this component was accessed via /ad/adId
      // (which means we are editing an ad, rather than adding a new one)
      const adId = this.route.snapshot.paramMap.get('ad');
      this.adService.findById(+adId).subscribe(data => {
        this.ad = data;
        this.existingAd = true;
        console.log(this.ad.fileName);
        document.getElementById('fileLabel').innerHTML = this.ad.fileName;
      });
    }
  }

  onSubmit() {
    log(this.ad.type);
    log(this.adTypes[AdType.LINEAR]);
    log(this.adTypes[this.ad.type]);
    if (this.existingAd) {
      this.adService.update(this.ad).subscribe(result => this.goToAdList());
    } else {
      // tslint:disable-next-line:triple-equals
      if (this.ad.type.toString() == this.adTypes[AdType.LINEAR]) { // if selected type of ad is linear, handle video upload and save ad
        this.fileService.uploadVideoFile(this.file).then((response) => {
          console.log(response);
          this.fail = false;
          this.adService.save(this.ad).subscribe(result => this.goToAdList());
        }, (reason => {
          console.log(reason);
          this.fail = true;
          document.getElementById('file-error').innerHTML = reason.error;
        }));
        // tslint:disable-next-line:triple-equals
      } else if (this.ad.type.toString() == this.adTypes[AdType.OVERLAY]) { // if selected type is overlay, handle image upload and save ad
        this.fileService.uploadImageFile(this.file).then((response) => {
          console.log(response);
          this.fail = false;
          this.adService.save(this.ad).subscribe(result => this.goToAdList());
        }, (reason => {
          console.log(reason);
          this.fail = true;
          document.getElementById('file-error').innerHTML = reason.error;
        }));
      }
    }
  }

  goToAdList() {
    this.router.navigate(['/']);
  }

  uploadFile(event) { // check if selected file is valid, prepare for upload when submit is pressed
    if (event.target.files.length > 0) {
      this.ad.fileName = event.target.files[0].name;
      document.getElementById('fileLabel').innerHTML = this.ad.fileName;

      if (event.target.files[0].size > 1e+7) {
        document.getElementById('file-error').innerHTML = 'File size exceeded limit';
        this.fail = true;
      } else {
          this.fail = false;
          this.file = event.target.files[0];
      }
    }
  }

  // shows preview - only called when editing an existing LINEAR ad
  showVideoPreview() {
    const options = {
      controls: true,
      autoplay: false,
      width: 'auto',
      height: 'auto',
      techOrder: ['html5'],
      sources: [{
        src: 'http://localhost:8080/files/' + this.ad.fileName,
        type: 'video/mp4'
      }]
    };
    this.vidObj = new videojs(this.vid.nativeElement, options, () => {
      videojs.log('Your player is ready!');
    });
    this.videoHidden = !this.videoHidden;

  }

  // shows ad preview, depending on ad type. if LINEAR, init video js player and show video, if OVERLAY, show image
  showPreview() {
    // tslint:disable-next-line:triple-equals
    if (this.ad.type.toString() == this.adTypes[AdType.LINEAR]) {
      this.showVideoPreview();
      // tslint:disable-next-line:triple-equals
    } else if (this.ad.type.toString() == this.adTypes[AdType.OVERLAY]) {
      this.imageHidden = !this.imageHidden;
    }
  }
}
