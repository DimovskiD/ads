import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ad, AdType} from '../../model/ad/ad';


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
  public findById(id: number): Observable<Ad> {
    return this.http.get<Ad>(this.adUrl + '/' + id);
  }
  public save(ad: Ad) {
    return this.http.post<Ad>(this.adUrl, ad);
  }

  public update(ad: Ad) {
    return this.http.patch<Ad>(this.adUrl + '/' + ad.id, ad);
  }

  public delete(adId: number) {
    return this.http.delete(this.adUrl + '/' + adId);
  }
  public getRandomAdByType(adType: AdType): Observable<Ad> {
    return this.http.get<Ad>(this.adUrl + '/type/' + adType);
  }

}
