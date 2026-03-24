import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';
import { IFile, NewFile } from '../file.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFile for edit and NewFileFormGroupInput for create.
 */
type FileFormGroupInput = IFile | PartialWithRequiredKeyOf<NewFile>;

type FileFormDefaults = {
  id: string | null;
  visibility: FileVisibility;
};

type FileFormGroupContent = {
  id: FormControl<IFile['id'] | NewFile['id']>;
  visibility: FormControl<IFile['visibility']>;
};

export type FileFormGroup = FormGroup<FileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FileFormService {
  createFileFormGroup(file?: FileFormGroupInput): FileFormGroup {
    const fileRawValue = {
      ...this.getFormDefaults(),
      ...(file ?? { id: null }),
    };
    return new FormGroup<FileFormGroupContent>({
      id: new FormControl(
        { value: fileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      visibility: new FormControl(fileRawValue.visibility, {
        nonNullable: true,
        validators: [Validators.required],
      }),
    });
  }

  getFile(form: FileFormGroup): IFile | NewFile {
    return form.getRawValue() as IFile | NewFile;
  }

  resetForm(form: FileFormGroup, file: FileFormGroupInput): void {
    const fileRawValue = { ...this.getFormDefaults(), ...file };
    form.reset(fileRawValue as any);
  }

  private getFormDefaults(): FileFormDefaults {
    return {
      id: null,
      visibility: FileVisibility.PRIVATE,
    };
  }
}
