
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdService } from '../ad.service';
import { Ad } from '../ad';


@Component({
  selector: 'app-ad-form',
  templateUrl: './ad-form.component.html',
  styleUrls: ['./ad-form.component.css']
})
export class AdFormComponent {

  ad: Ad;

  constructor(private route: ActivatedRoute, private router: Router, private adService: AdService) {
    this.ad = new Ad();
  }

  onSubmit() {
    this.adService.save(this.ad).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
