import {Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {FileService} from "../../service/file.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'image-view',
  templateUrl: './image-view.component.html',
  styleUrls: ['./image-view.component.scss']
})
export class ImageViewComponent implements OnInit {
  private image: any;
  private readonly imageType: string = 'data:image/jpg;base64,';

  constructor(private uploadService: FileService, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.uploadService.getImage()
      .subscribe(response => {
        if (response instanceof HttpResponse) {
          if (response.status == 200) {
            this.image = this.sanitizer.bypassSecurityTrustUrl(this.imageType + response.body.content);
          }
        }
      });
  }

}
