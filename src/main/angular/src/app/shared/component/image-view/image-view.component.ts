import {Component, OnInit} from '@angular/core';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {FileService} from '../../service/file.service';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'image-view',
  templateUrl: './image-view.component.html',
  styleUrls: ['./image-view.component.scss']
})
export class ImageViewComponent implements OnInit {
  image: SafeUrl;
  userHasImage = false;

  constructor(private fileService: FileService, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.fileService.getImage()
      .subscribe(response => {
          if (response instanceof HttpResponse) {
            if (response.status === 200) {
              this.userHasImage = true;
              this.image = this.sanitizer.bypassSecurityTrustUrl(response.body.content);
            }
          }
        },
        error => {
          if (error.status === 404) {
            this.userHasImage = false;
          }
        });
  }

}
