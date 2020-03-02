import { Component, OnInit } from '@angular/core';
import {FileService} from "../../service/file.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'file-upload',
  templateUrl: './file-upload.component.html'
})
export class FileUploadComponent implements OnInit {
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private uploadService: FileService) {}

  ngOnInit() {}

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.uploadImage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round((100 * event.loaded) / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFiles = undefined;
  }
}
