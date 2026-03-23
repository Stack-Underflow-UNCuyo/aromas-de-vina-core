import { Component, input } from '@angular/core';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IFile } from '../file.model';

@Component({
  selector: 'jhi-file-detail',
  templateUrl: './file-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule],
})
export class FileDetail {
  readonly file = input<IFile | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
