import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ad} from '../../model/ad/ad';


@Injectable({
  providedIn: 'root'
})export class AdService {

  private adUrl: string;
  constructor(private http: HttpClient) {
    this.adUrl = 'http://localhost:8080/ads';
  }

  public findAll(): Observable<Ad[]> {
    return this.http.get<Ad[]>(this.adUrl);
  }

  public save(ad: Ad) {
    return this.http.post<Ad>(this.adUrl, ad);
  }

  public delete(adId: number) {
    return this.http.delete(this.adUrl + '/' + adId);
  }

}
