import { Component, OnInit } from '@angular/core';
import {AdService} from '../../service/ad/ad.service';
import {Ad} from '../../model/ad/ad';

@Component({
  selector: 'app-ad',
  templateUrl: './ad.component.html',
  styleUrls: ['./ad.component.css']
})
export class AdComponent implements OnInit {

  ads: Ad[];

  constructor(private adService: AdService) { }

  ngOnInit() {
    this.adService.findAll().subscribe(data => {
      this.ads = data;
    });
  }
  delete(ad: Ad) {
    this.adService.delete(ad.id).subscribe(deleted => {
      console.log(deleted);
      if (deleted > 0) {
        this.ads.splice(this.ads.indexOf(ad), 1);
      }
    });
  }

}
