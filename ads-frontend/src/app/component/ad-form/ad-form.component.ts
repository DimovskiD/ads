import {Component, OnInit} from '@angular/core';
import {NgSelectOption} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AdService} from '../../service/ad/ad.service';
import {Ad, AdType, Category} from '../../model/ad/ad';
import {FileService} from '../../service/file/file.service';
import {log} from 'util';


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
  constructor(private route: ActivatedRoute, private router: Router, private adService: AdService, private fileService: FileService) {
    this.ad = new Ad();
    this.fail = false;
  }
  ngOnInit() {
    if (this.route.snapshot.paramMap.has('ad')) {
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
      if (this.ad.type.toString() == this.adTypes[AdType.LINEAR]) {
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
      } else if (this.ad.type.toString() == this.adTypes[AdType.OVERLAY]) {
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

  uploadFile(event) {
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


}
