import {Component, OnInit, ViewChild} from '@angular/core';
import {Article} from '../../shared/domain/article';
import {ActivatedRoute, Router} from '@angular/router';
import {GeneralConfirmationModalComponent} from '../../shared/component/general-confirmation-modal/general-confirmation-modal.component';
import {ArticleService} from '../../shared/service/article.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html'
})
export class ArticleDetailComponent implements OnInit {
  @ViewChild(GeneralConfirmationModalComponent) confirmDeletionModal: GeneralConfirmationModalComponent;
  article: Article;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private articleService: ArticleService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({article}) => {
      this.article = article;
    });
  }

  openConfirmationModal() {
    this.confirmDeletionModal.open();
  }

  onEventConfirmation(confirmed: boolean) {
    if (confirmed) {
      this.articleService.delete(this.article.id).subscribe(() => {
        this.router.navigateByUrl('/');
      });
    }
  }

}
