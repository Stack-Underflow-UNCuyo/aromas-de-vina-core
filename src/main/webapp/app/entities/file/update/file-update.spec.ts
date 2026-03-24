import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IFile } from '../file.model';
import { FileService } from '../service/file.service';

import { FileFormService } from './file-form.service';
import { FileUpdate } from './file-update';

describe('File Management Update Component', () => {
  let comp: FileUpdate;
  let fixture: ComponentFixture<FileUpdate>;
  let activatedRoute: ActivatedRoute;
  let fileFormService: FileFormService;
  let fileService: FileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(FileUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fileFormService = TestBed.inject(FileFormService);
    fileService = TestBed.inject(FileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const file: IFile = { id: '1c765935-8c23-4862-8c32-05427860e396' };

      activatedRoute.data = of({ file });
      comp.ngOnInit();

      expect(comp.file).toEqual(file);
    });
  });

  it('should call create service on save for new entity', () => {
    // GIVEN
    const saveSubject = new Subject<IFile>();
    const file = { id: 'b4cda08e-50b3-4383-a397-04fa73612acd' };
    vitest.spyOn(fileFormService, 'getFile').mockReturnValue({ id: null });
    vitest.spyOn(fileService, 'create').mockReturnValue(saveSubject);
    vitest.spyOn(comp, 'previousState');
    activatedRoute.data = of({ file: null });
    comp.ngOnInit();

    // WHEN
    comp.save();
    expect(comp.isSaving()).toEqual(true);
    saveSubject.next(file);
    saveSubject.complete();

    // THEN
    expect(fileFormService.getFile).toHaveBeenCalled();
    expect(fileService.create).toHaveBeenCalled();
    expect(comp.isSaving()).toEqual(false);
    expect(comp.previousState).toHaveBeenCalled();
  });
});
