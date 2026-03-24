import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, switchMap } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';
import { type IFile } from '../file.model';
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

  byteSize(file: File): string {
    return (file.size / 1024).toFixed(2) + ' KB';
  }

  setFileData(event: Event, field: string): void {
    const eventTarget: HTMLInputElement | null = event.target as HTMLInputElement | null;
    if (eventTarget?.files?.[0]) {
      const selectedFile: File = eventTarget.files[0];
      this.editForm.patchValue({
        [field]: selectedFile,
        contentType: selectedFile.type,
      });
    }
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const file = this.fileFormService.getFile(this.editForm);
    const selectedFile = this.editForm.get('file')?.value;
    const contentType = this.editForm.get('contentType')?.value;

    if (file.id === null && selectedFile && contentType && file.visibility) {
      this.subscribeToSaveResponse(
        this.fileService.create({ id: null, visibility: file.visibility, contentType }).pipe(
          switchMap(response => {
            const uploadUrl = response.url;
            if (!uploadUrl) {
              throw new Error('No upload URL returned');
            }
            return this.fileService.upload(uploadUrl, selectedFile);
          }),
        ),
      );
    }
  }

  protected subscribeToSaveResponse(result: Observable<unknown>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
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
