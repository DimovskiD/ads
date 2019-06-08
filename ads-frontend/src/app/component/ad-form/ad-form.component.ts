
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdService } from '../../service/ad/ad.service';
import { Ad } from '../../model/ad/ad';
import {FileService} from '../../service/file/file.service';


@Component({
  selector: 'app-ad-form',
  templateUrl: './ad-form.component.html',
  styleUrls: ['./ad-form.component.css']
})
export class AdFormComponent {

  ad: Ad;
  name: string;
  fail: boolean;
  file: File;
  constructor(private route: ActivatedRoute, private router: Router, private adService: AdService, private fileService: FileService) {
    this.ad = new Ad();
    this.fail = false;
  }

  onSubmit() {
    // if (this.ad.type === 0) {
    //   this.fileService.uploadVideoFile(this.file).then((response) => {
    //     console.log(response);
    //     this.fail = false;
    //     this.adService.save(this.ad).subscribe(result => this.goToAdList());
    //   }, (reason => {
    //     console.log(reason);
    //     this.fail = true;
    //     document.getElementById('file-error').innerHTML = reason.error;
    //   }));
    // } else if (this.ad.type === 1) {
      this.fileService.uploadImageFile(this.file).then((response) => {
        console.log(response);
        this.fail = false;
        this.adService.save(this.ad).subscribe(result => this.goToAdList());
      }, (reason => {
        console.log(reason);
        this.fail = true;
        document.getElementById('file-error').innerHTML = reason.error;
      }));
    // }
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
