import { Component, OnInit } from '@angular/core';
import {AdService} from '../ad.service';
import {Ad} from '../ad';

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

}
