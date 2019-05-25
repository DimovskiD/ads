import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private fileUpload: string;

  constructor(private http: HttpClient) {
    this.fileUpload = 'http://localhost:8080/files';
  }

  public async uploadFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return await this.http.post(this.fileUpload, formData, {responseType: 'text'}).toPromise();
    // subscribe((val) => {
    //   console.log(val);
    //   return val;
    // });
  }
}
