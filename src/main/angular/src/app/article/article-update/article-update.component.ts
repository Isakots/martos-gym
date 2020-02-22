import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ArticleService} from "../../shared/service/article.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Article} from "../../shared/domain/article";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {ChangeEvent} from "@ckeditor/ckeditor5-angular";

@Component({
  selector: 'app-article-update',
  templateUrl: './article-update.component.html',
  styleUrls: ['./article-update.component.scss']
})
export class ArticleUpdateComponent implements OnInit {
  isSaving: boolean;
  editorForm: FormGroup;
  public ck5editor = ClassicEditor;
  editorData: any;

  constructor(
    protected articleService: ArticleService,
    protected activatedRoute: ActivatedRoute,
    private _router: Router) {}


  ngOnInit() {
    this._initFormGroup();
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ article }) => {
      this.editorData = article.content;
      this.updateForm(article);
    });
  }

  public onChange( { editor }: ChangeEvent ) {
    this.editorData = editor.getData();
  }

  private _initFormGroup() {
    this.editorForm = new FormGroup({
      id: new FormControl(''),
      title: new FormControl(''),
      type: new FormControl(''),
      introduction: new FormControl('')
    });
  }

  updateForm(article: Article) {
    this.editorForm.patchValue({
      id: article.id,
      title: article.title,
      type: article.type,
      introduction: article.introduction
    });
  }

  save() {
    this.isSaving = true;
    const article = this.createFromForm();
    if (article.id !== undefined) {
      this.subscribeToSaveResponse(this.articleService.update(article));
    } else {
      this.subscribeToSaveResponse(this.articleService.create(article));
    }
  }

  private createFromForm(): Article {
    return {
      ...new Article(),
      id: this.editorForm.get(['id']).value,
      title: this.editorForm.get(['title']).value,
      type: this.editorForm.get(['type']).value,
      introduction: this.editorForm.get(['introduction']).value,
      content: this.editorData
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<Article>>) {
    result.subscribe(answer => this.onSaveSuccess(answer.body.id), () => this.onSaveError());
  }

  protected onSaveSuccess(id: string) {
    this.isSaving = false;
    this._router.navigate(['/articles', id, 'view']);
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

}
