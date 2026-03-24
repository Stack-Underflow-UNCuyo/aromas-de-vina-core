import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { type FileVisibility, IFile, NewFile } from '../file.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFile for edit and NewFileFormGroupInput for create.
 */
type FileFormGroupInput = IFile | PartialWithRequiredKeyOf<NewFile>;

type FileFormDefaults = Pick<NewFile, 'id' | 'visibility'>;

type FileFormGroupContent = {
  id: FormControl<IFile['id'] | NewFile['id']>;
  visibility: FormControl<NewFile['visibility']>;
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

  getFile(form: FileFormGroup): NewFile {
    return form.getRawValue() as NewFile;
  }

  resetForm(form: FileFormGroup, file: FileFormGroupInput): void {
    const fileRawValue = { ...this.getFormDefaults(), ...file };
    form.reset({
      ...fileRawValue,
      id: { value: fileRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): FileFormDefaults {
    return {
      id: null,
      visibility: 'PRIVATE',
    };
  }
}
