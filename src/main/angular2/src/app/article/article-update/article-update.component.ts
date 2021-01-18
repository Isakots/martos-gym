import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { ArticleService } from '../../core/services/article.service';
import { UserNotificationService } from '../../core/services/user-notification.service';
import { Article } from '../../shared/interfaces';

@Component({
    selector: 'app-article-update',
    templateUrl: './article-update.component.html',
    styleUrls: ['./article-update.component.scss']
})
export class ArticleUpdateComponent implements OnInit {
    editorForm!: FormGroup;
    title!: string;

    isSaving = false;

    constructor(private readonly articleService: ArticleService,
                private readonly activatedRoute: ActivatedRoute,
                private readonly router: Router,
                private readonly translate: TranslateService,
                private readonly userNotificationService: UserNotificationService) {
    }

    ngOnInit(): void {
        this.initFormGroup();
        this.setEditorContent();
        this.setTitle();
    }

    updateForm(article: Article): void {
        this.editorForm.patchValue({
            id: article.id,
            title: article.title,
            type: article.type,
            introduction: article.introduction
        });
    }

    save(): void {
        this.isSaving = true;
        const article = this.createFromForm();
        if (article.id !== undefined) {
            this.subscribeToSaveResponse(this.articleService.update(article));
        } else {
            this.subscribeToSaveResponse(this.articleService.create(article));
        }
    }

    private initFormGroup(): void {
        this.editorForm = new FormGroup({
            id: new FormControl(''),
            title: new FormControl('', Validators.required),
            type: new FormControl('Hírfolyam', Validators.required),
            introduction: new FormControl('', Validators.required)
        });
    }

    private setEditorContent(): void {
        this.activatedRoute.data.subscribe(({article}) => {
            // if (article.content !== undefined) {
            //     this.editorData = article.content;
            // } else {
            //     this.editorData = '<p></p>';
            // }
            this.updateForm(article);
        });
    }

    private setTitle(): void {
        if ('' === this.editorForm.controls.id.value) {
           this.translate.get('ARTICLES.SAVE_TITLE').subscribe(translation => this.title = translation);
        } else {
            this.translate.get('ARTICLES.UPDATE_TITLE').subscribe(translation => this.title = translation);
            this.editorForm.controls.type.disable();
        }
        // todo subscribe on lang change
    }

    private createFromForm(): Article {
        return {
            id: this.editorForm.controls.id.value,
            title: this.editorForm.controls.title.value,
            type: this.editorForm.controls.type.value,
            introduction: this.editorForm.controls.introduction.value,
            content: '',
            createdDate: ''
        };
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Article>>): void {
        result.subscribe(answer => this.onSaveSuccess(answer.body?.id),
            error => {
                if (409 === error.status) {
                    this.userNotificationService.notifyUser('Ilyen típusú cikk már létezik! Kérlek a meglévőt módosítsd!', true);
                }
                this.onSaveError();
            });
    }

    private onSaveSuccess(id: string | undefined): void {
        this.isSaving = false;
        this.router.navigate(['/articles', id, 'view']);
    }

    private onSaveError(): void {
        this.isSaving = false;
    }

}
