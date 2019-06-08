import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private imageUpload: string;
  private videoUpload: string;


  constructor(private http: HttpClient) {
    this.imageUpload = 'http://localhost:8080/files/img';
    this.videoUpload = 'http://localhost:8080/files/video';
  }

  public async uploadVideoFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return await this.http.post(this.videoUpload, formData, {responseType: 'text'}).toPromise();
    // subscribe((val) => {
    //   console.log(val);
    //   return val;
    // });
  }

  public async uploadImageFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return await this.http.post(this.imageUpload, formData, {responseType: 'text'}).toPromise();
    // subscribe((val) => {
    //   console.log(val);
    //   return val;
    // });
  }

  public async getFilePath(filename: string) {
    return await this.http.get(this.imageUpload + '/name/' + filename, {responseType: 'text'}).toPromise();
  }
}
