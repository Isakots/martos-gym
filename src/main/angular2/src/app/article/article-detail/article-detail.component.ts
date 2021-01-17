import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleService } from '../../core/services/article.service';
import { GeneralConfirmationModalComponent } from '../../shared/components/general-confirmation-modal/general-confirmation-modal.component';
import { Article } from '../../shared/interfaces';

@Component({
    selector: 'app-article-detail',
    templateUrl: './article-detail.component.html'
})
export class ArticleDetailComponent implements OnInit {
    @ViewChild(GeneralConfirmationModalComponent) confirmDeletionModal!: GeneralConfirmationModalComponent;
    article!: Article;

    constructor(private readonly activatedRoute: ActivatedRoute,
                private readonly articleService: ArticleService,
                private readonly router: Router) {
    }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({article}) => {
            this.article = article;
        });
    }

    openConfirmationModal(): void {
        this.confirmDeletionModal.open();
    }

    onEventConfirmation(confirmed: boolean): void {
        if (confirmed) {
            this.articleService.delete(this.article.id).subscribe(() => {
                this.router.navigateByUrl('/');
            });
        }
    }

}
