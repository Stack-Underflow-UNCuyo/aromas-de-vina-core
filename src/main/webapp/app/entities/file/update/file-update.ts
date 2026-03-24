import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { type NewFile, IFile } from '../file.model';
import { FileService } from '../service/file.service';

import { FileFormGroup, FileFormService } from './file-form.service';

@Component({
  selector: 'jhi-file-update',
  templateUrl: './file-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class FileUpdate implements OnInit {
  readonly isSaving = signal(false);
  file: IFile | null = null;
  fileVisibilityValues = Object.keys(FileVisibility);

  protected fileService = inject(FileService);
  protected fileFormService = inject(FileFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FileFormGroup = this.fileFormService.createFileFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ file }) => {
      this.file = file;
      if (file) {
        this.updateForm(file);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const file = this.fileFormService.getFile(this.editForm);
    if (file.id === null) {
      this.subscribeToSaveResponse(this.fileService.create(file));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IFile | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        console.warn('PUT URL:', response?.url);
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }

  protected updateForm(file: IFile): void {
    this.file = file;
    this.fileFormService.resetForm(this.editForm, file);
  }
}
